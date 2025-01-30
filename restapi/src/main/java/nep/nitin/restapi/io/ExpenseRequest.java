package nep.nitin.restapi.io;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ExpenseRequest {
    @NotBlank(message ="Expense Name is required")
    @Size( min =3, message ="Expense name should be atleast 3 characters" )
    private String name;
    @NotNull(message = "Expense note is required")
    private String note;
    @NotNull(message = "Expense Category is required")
    private String category;
    @NotNull(message = "Expense date is required")
    private Date date;
    @NotNull(message = "Expense amount is required")
    private BigDecimal amount;
}

