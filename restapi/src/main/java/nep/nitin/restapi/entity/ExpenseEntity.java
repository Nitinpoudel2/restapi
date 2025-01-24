package nep.nitin.restapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.Date;

@Entity
@Table(name = "tbl_expenses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)

    private String expenseId;

    private String name;

    private String note;

    private String category;

    private Date date;

    private BigDecimal amount;
    @CreationTimestamp
    private byte[] createdAt;
    @UpdateTimestamp
    private byte[] updatedAt;
}
