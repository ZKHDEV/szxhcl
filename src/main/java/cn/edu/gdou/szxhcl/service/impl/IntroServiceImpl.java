package cn.edu.gdou.szxhcl.service.impl;

import cn.edu.gdou.szxhcl.dao.IntroducesDao;
import cn.edu.gdou.szxhcl.model.Introduces;
import cn.edu.gdou.szxhcl.model.vo.Introduces.IntroducesVo;
import cn.edu.gdou.szxhcl.service.IntroService;
import cn.edu.gdou.szxhcl.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class IntroServiceImpl implements IntroService {
    @Autowired
    private IntroducesDao introducesDao;

    private IntroducesVo parseIntro(Introduces introduces){
        IntroducesVo introducesVo = new IntroducesVo();
        introducesVo.setId(introduces.getId());
        introducesVo.setContent(introduces.getContent());
        introducesVo.setUpdateDt(DateTimeUtil.dateToString(DateTimeUtil.YMDHMS, introduces.getUpdateDt()));

        return introducesVo;
    }

    @Override
    public IntroducesVo getFirst() {
        List<Introduces> introducesList = introducesDao.findAll();
        IntroducesVo introducesVo = null;
        if(introducesList != null && introducesList.size() > 0) {
            introducesVo = parseIntro(introducesList.get(0));
        } else {
            introducesVo = new IntroducesVo();
        }

        return introducesVo;

    }

    @Override
    public IntroducesVo save(IntroducesVo introducesVo) {
        List<Introduces> introducesList = introducesDao.findAll();
        Introduces introduces = null;
        if(introducesList != null && introducesList.size() > 0) {
            introduces = introducesList.get(0);
        }

        if(introduces == null) {
            introduces = new Introduces();
        }

        introduces.setContent(introducesVo.getContent());
        introduces.setUpdateDt(new Date());

        introducesDao.save(introduces);

        return parseIntro(introduces);
    }
}
