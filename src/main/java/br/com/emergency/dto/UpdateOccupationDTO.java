package br.com.emergency.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UpdateOccupationDTO {
    @NotNull
    private Long id;
    @Min(0)
    private int occupation;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public int getOccupation() { return occupation; }
    public void setOccupation(int occupation) { this.occupation = occupation; }
}
