package com.hk.library.Book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class BookDTO {

    @Data
    @AllArgsConstructor
    @Builder
    public static class PostInput{

        String bookName;
        Integer bookPages;
    }

}
