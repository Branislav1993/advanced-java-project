package rs.fon.parlament.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "obracanje")
public class Speech {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "idobracanje")
	private Integer id;

	@Column(name = "tekst")
	private String text;

	@ManyToOne
	@JoinColumn(name = "idposlanika")
	private Member member;

	@Column(name = "idplenarnesednice")
	private Integer plenarySessionId;

	@Column(name = "datumsednice")
	private Date sessionDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Integer getPlenarySessionId() {
		return plenarySessionId;
	}

	public void setPlenarySession(Integer plenarySessionId) {
		this.plenarySessionId = plenarySessionId;
	}

	public Date getSessionDate() {
		return sessionDate;
	}

	public void setSessionDate(Date sessionDate) {
		this.sessionDate = sessionDate;
	}

	@Override
	public String toString() {
		return "Speech [id=" + id + ", text=" + text + ", plenarnaSednicaID=" + plenarySessionId + ", sessionDate="
				+ sessionDate + "]";
	}

}
