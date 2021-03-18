package com.a2sdm.need.doctors.model.test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.sql.Delete;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TestResponse {
    public Insert insert;
    public Delete delete;
}
