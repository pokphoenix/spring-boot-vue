package vertice.edsbe.web.model;

import lombok.Data;

import javax.validation.Valid;

@Data
public class PageVO {
    private String query;
    private int page =1;
    private int limit = 10;

    @Valid
    private SortVO sort;
}
