package cn.edu.gdou.szxhcl.service;

import cn.edu.gdou.szxhcl.model.vo.Introduces.IntroducesVo;

public interface IntroService {
    IntroducesVo getFirst();
    IntroducesVo save(IntroducesVo introducesVo);
}
