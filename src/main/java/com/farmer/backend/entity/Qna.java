package com.farmer.backend.entity;

import com.farmer.backend.dto.admin.board.qna.RequestBoardQnADto;
import com.farmer.backend.dto.admin.board.qna.ResponseBoardQnADto;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Qna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull
    @Column(length = 80)
    private String subject;

    @NotNull
    @Column(columnDefinition = "text")
    private String content;

    @Column(columnDefinition = "text")
    private String answer;

    @NotNull
    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime qCreatedDate;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd/HH:mm:ss")
    private LocalDateTime aCreatedDate;

    public ResponseBoardQnADto qnaList() {
        return ResponseBoardQnADto.builder()
                .id(id)
                .memberEmail(member.getEmail())
                .memberName(member.getUsername())
                .productName(product.getName())
                .subject(subject)
                .content(content)
                .answer(answer)
                .qCreatedDate(qCreatedDate)
                .aCreatedDate(aCreatedDate)
                .build();
    }


    public void qnaAnswer(RequestBoardQnADto qnaDto){
        this.answer=qnaDto.getAnswer();
        this.aCreatedDate=LocalDateTime.now();
    }


}
