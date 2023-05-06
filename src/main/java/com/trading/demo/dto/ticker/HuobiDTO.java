package com.trading.demo.dto.ticker;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class HuobiDTO {
    @Getter
    private List<HuobiTickerDTO> data;
}
