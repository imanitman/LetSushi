package com.sushi.uddd.Domain.Response;

import com.sushi.uddd.Domain.Line;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResOrderAdmin {
    private List<ResLineDto> lines;
}
