package com.amplifyframework.datastore.generated.model;


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

/** This is an auto generated class representing the SensorReading type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "SensorReadings")
public final class SensorReading implements Model {
  public static final QueryField ID = field("SensorReading", "id");
  public static final QueryField READING_ID = field("SensorReading", "readingID");
  public static final QueryField USER_ID = field("SensorReading", "userID");
  public static final QueryField LOCATION = field("SensorReading", "location");
  public static final QueryField AQI = field("SensorReading", "aqi");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String readingID;
  private final @ModelField(targetType="ID", isRequired = true) String userID;
  private final @ModelField(targetType="String") String location;
  private final @ModelField(targetType="Float", isRequired = true) Double aqi;
  public String getId() {
      return id;
  }
  
  public String getReadingId() {
      return readingID;
  }
  
  public String getUserId() {
      return userID;
  }
  
  public String getLocation() {
      return location;
  }
  
  public Double getAqi() {
      return aqi;
  }
  
  private SensorReading(String id, String readingID, String userID, String location, Double aqi) {
    this.id = id;
    this.readingID = readingID;
    this.userID = userID;
    this.location = location;
    this.aqi = aqi;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      SensorReading sensorReading = (SensorReading) obj;
      return ObjectsCompat.equals(getId(), sensorReading.getId()) &&
              ObjectsCompat.equals(getReadingId(), sensorReading.getReadingId()) &&
              ObjectsCompat.equals(getUserId(), sensorReading.getUserId()) &&
              ObjectsCompat.equals(getLocation(), sensorReading.getLocation()) &&
              ObjectsCompat.equals(getAqi(), sensorReading.getAqi());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getReadingId())
      .append(getUserId())
      .append(getLocation())
      .append(getAqi())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("SensorReading {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("readingID=" + String.valueOf(getReadingId()) + ", ")
      .append("userID=" + String.valueOf(getUserId()) + ", ")
      .append("location=" + String.valueOf(getLocation()) + ", ")
      .append("aqi=" + String.valueOf(getAqi()))
      .append("}")
      .toString();
  }
  
  public static ReadingIdStep builder() {
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
  public static SensorReading justId(String id) {
    try {
      UUID.fromString(id); // Check that ID is in the UUID format - if not an exception is thrown
    } catch (Exception exception) {
      throw new IllegalArgumentException(
              "Model IDs must be unique in the format of UUID. This method is for creating instances " +
              "of an existing object with only its ID field for sending as a mutation parameter. When " +
              "creating a new object, use the standard builder method and leave the ID field blank."
      );
    }
    return new SensorReading(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      readingID,
      userID,
      location,
      aqi);
  }
  public interface ReadingIdStep {
    UserIdStep readingId(String readingId);
  }
  

  public interface UserIdStep {
    AqiStep userId(String userId);
  }
  

  public interface AqiStep {
    BuildStep aqi(Double aqi);
  }
  

  public interface BuildStep {
    SensorReading build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep location(String location);
  }
  

  public static class Builder implements ReadingIdStep, UserIdStep, AqiStep, BuildStep {
    private String id;
    private String readingID;
    private String userID;
    private Double aqi;
    private String location;
    @Override
     public SensorReading build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new SensorReading(
          id,
          readingID,
          userID,
          location,
          aqi);
    }
    
    @Override
     public UserIdStep readingId(String readingId) {
        Objects.requireNonNull(readingId);
        this.readingID = readingId;
        return this;
    }
    
    @Override
     public AqiStep userId(String userId) {
        Objects.requireNonNull(userId);
        this.userID = userId;
        return this;
    }
    
    @Override
     public BuildStep aqi(Double aqi) {
        Objects.requireNonNull(aqi);
        this.aqi = aqi;
        return this;
    }
    
    @Override
     public BuildStep location(String location) {
        this.location = location;
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
    private CopyOfBuilder(String id, String readingId, String userId, String location, Double aqi) {
      super.id(id);
      super.readingId(readingId)
        .userId(userId)
        .aqi(aqi)
        .location(location);
    }
    
    @Override
     public CopyOfBuilder readingId(String readingId) {
      return (CopyOfBuilder) super.readingId(readingId);
    }
    
    @Override
     public CopyOfBuilder userId(String userId) {
      return (CopyOfBuilder) super.userId(userId);
    }
    
    @Override
     public CopyOfBuilder aqi(Double aqi) {
      return (CopyOfBuilder) super.aqi(aqi);
    }
    
    @Override
     public CopyOfBuilder location(String location) {
      return (CopyOfBuilder) super.location(location);
    }
  }
  
}
