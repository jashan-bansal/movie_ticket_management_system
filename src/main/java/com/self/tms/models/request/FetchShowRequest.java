package com.self.tms.models.request;

import lombok.Data;

import java.util.UUID;

@Data
public class FetchShowRequest {
    UUID id;
    String searchType;
}
