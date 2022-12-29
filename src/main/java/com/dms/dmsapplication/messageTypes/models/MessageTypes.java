package com.dms.dmsapplication.messageTypes.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "messages_types",
       uniqueConstraints = {
        @UniqueConstraint(columnNames = "message_type")
       }
       )
public class MessageTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "public.message_types_id_seq")
    @SequenceGenerator(name = "public.message_types_id_seq",
                       sequenceName = "public.message_types_id_seq", allocationSize = 1)
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column(name = "message_type")
    private String messageType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

}
