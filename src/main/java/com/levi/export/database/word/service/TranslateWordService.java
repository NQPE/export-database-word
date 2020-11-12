package com.levi.export.database.word.service;

public interface TranslateWordService {
    /**
     * 翻译接口
     * @param content 翻译内容
     * @param from 来源目标语言 zh
     * @param to 翻译目标语言 en
     * @return
     */
    String translateWord(String content,String from,String to);
}
