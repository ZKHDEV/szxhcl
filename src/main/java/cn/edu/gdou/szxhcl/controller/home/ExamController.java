package cn.edu.gdou.szxhcl.controller.home;

import cn.edu.gdou.szxhcl.controller.BaseController;
import cn.edu.gdou.szxhcl.model.vo.article.ArticleClassVo;
import cn.edu.gdou.szxhcl.model.vo.exam.ChoiceVo;
import cn.edu.gdou.szxhcl.model.vo.exam.ExamVo;
import cn.edu.gdou.szxhcl.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("exam")
public class ExamController extends BaseController {
    @Autowired
    private ExamService examService;

    @GetMapping("")
    public String list(ModelMap model, @ModelAttribute List<ArticleClassVo> articleClassVoList){
        List<ExamVo> examList = examService.getExamList();
        if(examList != null && examList.size() > 0){
            ExamVo exam = null;
            List<ChoiceVo> choiceList = null;
            for(Iterator<ExamVo> iterable = examList.iterator(); iterable.hasNext();) {
                exam = iterable.next();
                choiceList = exam.getChoiceList();
                if(choiceList == null || choiceList.size() <= 0) {
                    iterable.remove();
                }
            }
        }

        model.put("examList", examList);
        model.put("classList", articleClassVoList);
        return view("home/exam-list");
    }

    @GetMapping("{id}")
    public String exam(ModelMap model, @PathVariable("id") String id, @ModelAttribute List<ArticleClassVo> articleClassVoList){
        ExamVo exam = examService.getExam(id, true);
        List<ExamVo> examList = examService.getExamList();

        model.put("model", exam);
        model.put("examList", examList);
        model.put("resType", "question");
        model.put("classList", articleClassVoList);
        return view("home/exam");
    }

    @PostMapping("{examId}")
    public String check(ModelMap model, @PathVariable("examId") String examId, String userAns, @ModelAttribute List<ArticleClassVo> articleClassVoList){
        ExamVo exam = examService.getExam(examId, true);
        List<ChoiceVo> choiceList = exam.getChoiceList();
        int count = choiceList.size();

        String[] ansArr = userAns.split(",");

        List<ChoiceVo> ansChoiceList = new ArrayList<>();
        for(ChoiceVo choice : choiceList) {
            choice.setUserAns(ansArr[choice.getNo() - 1]);
            ansChoiceList.add(choice);
        }
        exam.setChoiceList(ansChoiceList);

        List<ExamVo> examList = examService.getExamList();

        model.put("model", exam);
        model.put("examList", examList);
        model.put("resType", "result");
        model.put("classList", articleClassVoList);
        return view("home/exam");
    }

}
