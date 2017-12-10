package org.repoaggr.svnbrk.model;

import java.io.Serializable;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * OverviewData
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-12-09T09:35:53.477Z")

public class OverviewData implements Serializable {
  @JsonProperty("last_sych_date")
  private BigDecimal lastSychDate = null;

  @JsonProperty("repo_type")
  private String repoType = null;

  @JsonProperty("url")
  private String url = null;

  @JsonProperty("size")
  private BigDecimal size = null;

  private String login = null;
  private String password = null;

  public OverviewData(
          BigDecimal lastSychDate,
          String repoType,
          String url,
          BigDecimal size
  ) {
      this.lastSychDate = lastSychDate;
      this.repoType = repoType;
      this.url = url;
      this.size = size;
  }

  public OverviewData lastSychDate(BigDecimal lastSychDate) {
    this.lastSychDate = lastSychDate;
    return this;
  }

   /**
   * Get lastSychDate
   * @return lastSychDate
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getLastSychDate() {
    return lastSychDate;
  }

  public void setLastSychDate(BigDecimal lastSychDate) {
    this.lastSychDate = lastSychDate;
  }

  public OverviewData repoType(String repoType) {
    this.repoType = repoType;
    return this;
  }

   /**
   * Get repoType
   * @return repoType
  **/
  @ApiModelProperty(value = "")

 @Pattern(regexp="svn")
  public String getRepoType() {
    return repoType;
  }

  public void setRepoType(String repoType) {
    this.repoType = repoType;
  }

  public OverviewData url(String url) {
    this.url = url;
    return this;
  }

   /**
   * Get url
   * @return url
  **/
  @ApiModelProperty(value = "")


  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public OverviewData size(BigDecimal size) {
    this.size = size;
    return this;
  }

   /**
   * Get size
   * @return size
  **/
  @ApiModelProperty(value = "")

  @Valid

  public BigDecimal getSize() {
    return size;
  }

  public void setSize(BigDecimal size) {
    this.size = size;
  }


  // Получение логина и пароля ------------------------------------------------
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    // Задание логина и пароля ------------------------------------------------
    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OverviewData overviewData = (OverviewData) o;
    return Objects.equals(this.lastSychDate, overviewData.lastSychDate) &&
        Objects.equals(this.repoType, overviewData.repoType) &&
        Objects.equals(this.url, overviewData.url) &&
        Objects.equals(this.size, overviewData.size);
  }

  @Override
  public int hashCode() {
    return Objects.hash(lastSychDate, repoType, url, size);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class OverviewData {\n");
    
    sb.append("    lastSychDate: ").append(toIndentedString(lastSychDate)).append("\n");
    sb.append("    repoType: ").append(toIndentedString(repoType)).append("\n");
    sb.append("    url: ").append(toIndentedString(url)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

