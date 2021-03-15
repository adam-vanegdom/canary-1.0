package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Profiles type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Profiles")
public final class Profiles implements Model {
  public static final QueryField ID = field("Profiles", "id");
  public static final QueryField PROFILE_ID = field("Profiles", "profileID");
  public static final QueryField USER_ID = field("Profiles", "userID");
  public static final QueryField GENDER = field("Profiles", "gender");
  public static final QueryField DOB = field("Profiles", "dob");
  public static final QueryField RESP_COND = field("Profiles", "respCond");
  public static final QueryField ACTIVITY = field("Profiles", "activity");
  public static final QueryField DIET = field("Profiles", "diet");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String profileID;
  private final @ModelField(targetType="ID", isRequired = true) String userID;
  private final @ModelField(targetType="Gender", isRequired = true) Gender gender;
  private final @ModelField(targetType="AWSDateTime", isRequired = true) Temporal.DateTime dob;
  private final @ModelField(targetType="String") String respCond;
  private final @ModelField(targetType="String") String activity;
  private final @ModelField(targetType="String") String diet;
  public String getId() {
      return id;
  }
  
  public String getProfileId() {
      return profileID;
  }
  
  public String getUserId() {
      return userID;
  }
  
  public Gender getGender() {
      return gender;
  }
  
  public Temporal.DateTime getDob() {
      return dob;
  }
  
  public String getRespCond() {
      return respCond;
  }
  
  public String getActivity() {
      return activity;
  }
  
  public String getDiet() {
      return diet;
  }
  
  private Profiles(String id, String profileID, String userID, Gender gender, Temporal.DateTime dob, String respCond, String activity, String diet) {
    this.id = id;
    this.profileID = profileID;
    this.userID = userID;
    this.gender = gender;
    this.dob = dob;
    this.respCond = respCond;
    this.activity = activity;
    this.diet = diet;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Profiles profiles = (Profiles) obj;
      return ObjectsCompat.equals(getId(), profiles.getId()) &&
              ObjectsCompat.equals(getProfileId(), profiles.getProfileId()) &&
              ObjectsCompat.equals(getUserId(), profiles.getUserId()) &&
              ObjectsCompat.equals(getGender(), profiles.getGender()) &&
              ObjectsCompat.equals(getDob(), profiles.getDob()) &&
              ObjectsCompat.equals(getRespCond(), profiles.getRespCond()) &&
              ObjectsCompat.equals(getActivity(), profiles.getActivity()) &&
              ObjectsCompat.equals(getDiet(), profiles.getDiet());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getProfileId())
      .append(getUserId())
      .append(getGender())
      .append(getDob())
      .append(getRespCond())
      .append(getActivity())
      .append(getDiet())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Profiles {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("profileID=" + String.valueOf(getProfileId()) + ", ")
      .append("userID=" + String.valueOf(getUserId()) + ", ")
      .append("gender=" + String.valueOf(getGender()) + ", ")
      .append("dob=" + String.valueOf(getDob()) + ", ")
      .append("respCond=" + String.valueOf(getRespCond()) + ", ")
      .append("activity=" + String.valueOf(getActivity()) + ", ")
      .append("diet=" + String.valueOf(getDiet()))
      .append("}")
      .toString();
  }
  
  public static ProfileIdStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   * @throws IllegalArgumentException Checks that ID is in the proper format
   */
  public static Profiles justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new Profiles(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      profileID,
      userID,
      gender,
      dob,
      respCond,
      activity,
      diet);
  }
  public interface ProfileIdStep {
    UserIdStep profileId(String profileId);
  }
  

  public interface UserIdStep {
    GenderStep userId(String userId);
  }
  

  public interface GenderStep {
    DobStep gender(Gender gender);
  }
  

  public interface DobStep {
    BuildStep dob(Temporal.DateTime dob);
  }
  

  public interface BuildStep {
    Profiles build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep respCond(String respCond);
    BuildStep activity(String activity);
    BuildStep diet(String diet);
  }
  

  public static class Builder implements ProfileIdStep, UserIdStep, GenderStep, DobStep, BuildStep {
    private String id;
    private String profileID;
    private String userID;
    private Gender gender;
    private Temporal.DateTime dob;
    private String respCond;
    private String activity;
    private String diet;
    @Override
     public Profiles build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Profiles(
          id,
          profileID,
          userID,
          gender,
          dob,
          respCond,
          activity,
          diet);
    }
    
    @Override
     public UserIdStep profileId(String profileId) {
        Objects.requireNonNull(profileId);
        this.profileID = profileId;
        return this;
    }
    
    @Override
     public GenderStep userId(String userId) {
        Objects.requireNonNull(userId);
        this.userID = userId;
        return this;
    }
    
    @Override
     public DobStep gender(Gender gender) {
        Objects.requireNonNull(gender);
        this.gender = gender;
        return this;
    }
    
    @Override
     public BuildStep dob(Temporal.DateTime dob) {
        Objects.requireNonNull(dob);
        this.dob = dob;
        return this;
    }
    
    @Override
     public BuildStep respCond(String respCond) {
        this.respCond = respCond;
        return this;
    }
    
    @Override
     public BuildStep activity(String activity) {
        this.activity = activity;
        return this;
    }
    
    @Override
     public BuildStep diet(String diet) {
        this.diet = diet;
        return this;
    }
    
    /** 
     * WARNING: Do not set ID when creating a new object. Leave this blank and one will be auto generated for you.
     * This should only be set when referring to an already existing object.
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     * @throws IllegalArgumentException Checks that ID is in the proper format
     */
    public BuildStep id(String id) throws IllegalArgumentException {
        this.id = id;
        
        try {
            UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
        } catch (Exception exception) {
          throw new IllegalArgumentException("Model IDs must be unique in the format of UUID.",
                    exception);
        }
        
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String profileId, String userId, Gender gender, Temporal.DateTime dob, String respCond, String activity, String diet) {
      super.id(id);
      super.profileId(profileId)
        .userId(userId)
        .gender(gender)
        .dob(dob)
        .respCond(respCond)
        .activity(activity)
        .diet(diet);
    }
    
    @Override
     public CopyOfBuilder profileId(String profileId) {
      return (CopyOfBuilder) super.profileId(profileId);
    }
    
    @Override
     public CopyOfBuilder userId(String userId) {
      return (CopyOfBuilder) super.userId(userId);
    }
    
    @Override
     public CopyOfBuilder gender(Gender gender) {
      return (CopyOfBuilder) super.gender(gender);
    }
    
    @Override
     public CopyOfBuilder dob(Temporal.DateTime dob) {
      return (CopyOfBuilder) super.dob(dob);
    }
    
    @Override
     public CopyOfBuilder respCond(String respCond) {
      return (CopyOfBuilder) super.respCond(respCond);
    }
    
    @Override
     public CopyOfBuilder activity(String activity) {
      return (CopyOfBuilder) super.activity(activity);
    }
    
    @Override
     public CopyOfBuilder diet(String diet) {
      return (CopyOfBuilder) super.diet(diet);
    }
  }
  
}
