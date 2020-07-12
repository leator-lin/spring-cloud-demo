package com.define.dto;

import com.sun.prism.impl.Disposer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    private UserDTO userDTO;
    private RecordDTO recordDTO;
}
