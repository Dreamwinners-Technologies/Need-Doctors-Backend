package com.a2sdm.need.doctors.model.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Pagination {
    public int total;
    public int count;
    public int per_page;
    public int current_page;
    public int total_pages;
    public Links links;
}
