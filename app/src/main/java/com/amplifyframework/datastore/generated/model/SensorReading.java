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
@Index(name = "readingsByDate", fields = {"userID","createdAt"})
public final class SensorReading implements Model {
  public static final QueryField ID = field("SensorReading", "id");
  public static final QueryField READING_ID = field("SensorReading", "readingID");
  public static final QueryField USER_ID = field("SensorReading", "userID");
  public static final QueryField CREATED_AT = field("SensorReading", "createdAt");
  public static final QueryField LOCATION = field("SensorReading", "location");
  public static final QueryField AQI = field("SensorReading", "aqi");
  public static final QueryField VOC = field("SensorReading", "voc");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String readingID;
  private final @ModelField(targetType="ID", isRequired = true) String userID;
  private final @ModelField(targetType="String", isRequired = true) String createdAt;
  private final @ModelField(targetType="String") String location;
  private final @ModelField(targetType="Float", isRequired = true) Double aqi;
  private final @ModelField(targetType="Float", isRequired = true) Double voc;
  public String getId() {
      return id;
  }
  
  public String getReadingId() {
      return readingID;
  }
  
  public String getUserId() {
      return userID;
  }
  
  public String getCreatedAt() {
      return createdAt;
  }
  
  public String getLocation() {
      return location;
  }
  
  public Double getAqi() {
      return aqi;
  }
  
  public Double getVoc() {
      return voc;
  }
  
  private SensorReading(String id, String readingID, String userID, String createdAt, String location, Double aqi, Double voc) {
    this.id = id;
    this.readingID = readingID;
    this.userID = userID;
    this.createdAt = createdAt;
    this.location = location;
    this.aqi = aqi;
    this.voc = voc;
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
              ObjectsCompat.equals(getCreatedAt(), sensorReading.getCreatedAt()) &&
              ObjectsCompat.equals(getLocation(), sensorReading.getLocation()) &&
              ObjectsCompat.equals(getAqi(), sensorReading.getAqi()) &&
              ObjectsCompat.equals(getVoc(), sensorReading.getVoc());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getReadingId())
      .append(getUserId())
      .append(getCreatedAt())
      .append(getLocation())
      .append(getAqi())
      .append(getVoc())
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
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("location=" + String.valueOf(getLocation()) + ", ")
      .append("aqi=" + String.valueOf(getAqi()) + ", ")
      .append("voc=" + String.valueOf(getVoc()))
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
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      readingID,
      userID,
      createdAt,
      location,
      aqi,
      voc);
  }
  public interface ReadingIdStep {
    UserIdStep readingId(String readingId);
  }
  

  public interface UserIdStep {
    CreatedAtStep userId(String userId);
  }
  

  public interface CreatedAtStep {
    AqiStep createdAt(String createdAt);
  }
  

  public interface AqiStep {
    VocStep aqi(Double aqi);
  }
  

  public interface VocStep {
    BuildStep voc(Double voc);
  }
  

  public interface BuildStep {
    SensorReading build();
    BuildStep id(String id) throws IllegalArgumentException;
    BuildStep location(String location);
  }
  

  public static class Builder implements ReadingIdStep, UserIdStep, CreatedAtStep, AqiStep, VocStep, BuildStep {
    private String id;
    private String readingID;
    private String userID;
    private String createdAt;
    private Double aqi;
    private Double voc;
    private String location;
    @Override
     public SensorReading build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new SensorReading(
          id,
          readingID,
          userID,
          createdAt,
          location,
          aqi,
          voc);
    }
    
    @Override
     public UserIdStep readingId(String readingId) {
        Objects.requireNonNull(readingId);
        this.readingID = readingId;
        return this;
    }
    
    @Override
     public CreatedAtStep userId(String userId) {
        Objects.requireNonNull(userId);
        this.userID = userId;
        return this;
    }
    
    @Override
     public AqiStep createdAt(String createdAt) {
        Objects.requireNonNull(createdAt);
        this.createdAt = createdAt;
        return this;
    }
    
    @Override
     public VocStep aqi(Double aqi) {
        Objects.requireNonNull(aqi);
        this.aqi = aqi;
        return this;
    }
    
    @Override
     public BuildStep voc(Double voc) {
        Objects.requireNonNull(voc);
        this.voc = voc;
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
    private CopyOfBuilder(String id, String readingId, String userId, String createdAt, String location, Double aqi, Double voc) {
      super.id(id);
      super.readingId(readingId)
        .userId(userId)
        .createdAt(createdAt)
        .aqi(aqi)
        .voc(voc)
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
     public CopyOfBuilder createdAt(String createdAt) {
      return (CopyOfBuilder) super.createdAt(createdAt);
    }
    
    @Override
     public CopyOfBuilder aqi(Double aqi) {
      return (CopyOfBuilder) super.aqi(aqi);
    }
    
    @Override
     public CopyOfBuilder voc(Double voc) {
      return (CopyOfBuilder) super.voc(voc);
    }
    
    @Override
     public CopyOfBuilder location(String location) {
      return (CopyOfBuilder) super.location(location);
    }
  }
  
}
