package com.bl.entity.cms;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the main_page_blocks database table.
 * 
 */
@Entity
@Table(name="main_page_blocks")
@NamedQuery(name="MainPageBlockEntity.findAll", query="SELECT m FROM MainPageBlockEntity m")
public class MainPageBlockEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="BLOCK_FIVE_DESC")
	private String blockFiveDesc;

	@Column(name="BLOCK_FIVE_PIC")
	private String blockFivePic;

	@Column(name="BLOCK_FOUR_DESC")
	private String blockFourDesc;

	@Column(name="BLOCK_FOUR_PIC")
	private String blockFourPic;

	@Column(name="BLOCK_ONE_DESC")
	private String blockOneDesc;

	@Column(name="BLOCK_ONE_PIC")
	private String blockOnePic;

	@Column(name="BLOCK_SIX_DESC")
	private String blockSixDesc;

	@Column(name="BLOCK_SIX_PIC")
	private String blockSixPic;

	@Column(name="BLOCK_THREE_DESC")
	private String blockThreeDesc;

	@Column(name="BLOCK_THREE_PIC")
	private String blockThreePic;
 
	@Column(name="BLOCK_TWO_DESC")
	private String blockTwoDesc;

	@Column(name="BLOCK_TWO_PIC")
	private String blockTwoPic;

	@Column(name="CREATED_BY")
	private Integer createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name="IS_ACTIVE")
	private Integer isActive;

	@Column(name="UPDATED_BY")
	private Integer updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="UPDATED_DATE")
	private Date updatedDate;



	

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBlockFiveDesc() {
		return this.blockFiveDesc;
	}

	public void setBlockFiveDesc(String blockFiveDesc) {
		this.blockFiveDesc = blockFiveDesc;
	}

	public String getBlockFivePic() {
		return this.blockFivePic;
	}

	public void setBlockFivePic(String blockFivePic) {
		this.blockFivePic = blockFivePic;
	}

	public String getBlockFourDesc() {
		return this.blockFourDesc;
	}

	public void setBlockFourDesc(String blockFourDesc) {
		this.blockFourDesc = blockFourDesc;
	}

	public String getBlockFourPic() {
		return this.blockFourPic;
	}

	public void setBlockFourPic(String blockFourPic) {
		this.blockFourPic = blockFourPic;
	}

	public String getBlockOneDesc() {
		return this.blockOneDesc;
	}

	public void setBlockOneDesc(String blockOneDesc) {
		this.blockOneDesc = blockOneDesc;
	}

	public String getBlockOnePic() {
		return this.blockOnePic;
	}

	public void setBlockOnePic(String blockOnePic) {
		this.blockOnePic = blockOnePic;
	}

	public String getBlockSixDesc() {
		return this.blockSixDesc;
	}

	public void setBlockSixDesc(String blockSixDesc) {
		this.blockSixDesc = blockSixDesc;
	}

	public String getBlockSixPic() {
		return this.blockSixPic;
	}

	public void setBlockSixPic(String blockSixPic) {
		this.blockSixPic = blockSixPic;
	}

	public String getBlockThreeDesc() {
		return this.blockThreeDesc;
	}

	public void setBlockThreeDesc(String blockThreeDesc) {
		this.blockThreeDesc = blockThreeDesc;
	}

	public String getBlockThreePic() {
		return this.blockThreePic;
	}

	public void setBlockThreePic(String blockThreePic) {
		this.blockThreePic = blockThreePic;
	}

	public String getBlockTwoDesc() {
		return this.blockTwoDesc;
	}

	public void setBlockTwoDesc(String blockTwoDesc) {
		this.blockTwoDesc = blockTwoDesc;
	}

	public String getBlockTwoPic() {
		return this.blockTwoPic;
	}

	public void setBlockTwoPic(String blockTwoPic) {
		this.blockTwoPic = blockTwoPic;
	}

	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public Integer getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

}