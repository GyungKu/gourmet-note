package com.gk.gourmet_note.image.vo;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DeleteRequestImages {
    private List<String> saveNames = new ArrayList<>();
}
