package com.sushi.uddd.Domain.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqDeleteFood {
    private long idLine;
    private long idUser;
}
