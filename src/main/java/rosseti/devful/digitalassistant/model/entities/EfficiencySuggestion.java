package rosseti.devful.digitalassistant.model.entities;

import lombok.Data;
import rosseti.devful.digitalassistant.model.Status;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "suggestions")
public class EfficiencySuggestion {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "reg_number")
    private String regNumber;

    @Column(name = "create_date")
    private Long createDate;

    @Column(name = "create_year")
    private Integer createYear;

    @Column(name = "name")
    private String name;

    @Column(name = "authors")
    @ElementCollection(targetClass=String.class)
    private List<String> authors;

    @Column(name = "authors_positions")
    @ElementCollection(targetClass=String.class)
    private List<String> authorsPositions;

    @Column(name = "proposal_scope")
    private String proposalScope;

    @Column(name = "proposal_category")
    private String proposalCategory;

    @Column(name = "status")
    private Status status;

    @Column(name = "status_update_date")
    private Long statusUpdateDate;

    @Column(name = "priority")
    private String priority;

    @Column(name = "proposal_moving")
    private String proposalMoving;

    @Column(name = "economical_effect")
    private Integer economicalEffect;

    @Column(name = "bounty_kind")
    private String bountyKind;

    @Column(name = "bounty_int")
    private String requisitesBountyIntroductionKind;

    @Column(name = "bounty_int_summ")
    private int bountyIntroductionSumm;

    @Column(name = "bounty_repl")
    private String requisitesBountyReplication;

    @Column(name = "bounty_repl_summ")
    private int	bountyReplicationSumm;

    @Column(name = "comment")
    private String comment;



}
