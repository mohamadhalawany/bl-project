package com.bl.entity.cms;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the faq database table.
 * 
 */
@Entity
@Table(name="faq")
@NamedQuery(name="FaqEntity.findAll", query="SELECT f FROM FaqEntity f")
public class FaqEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "ANSWER")
	private String answer;

	@Column(name = "QUESTION")
	private String question;



	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

}