package com.tincore.gsp.server.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@DiscriminatorValue(value = "S")
@Table(indexes = { //
		@Index(name = "sess_date_start_idx", columnList = "dateStart", unique = false), //
		@Index(name = "sess_date_end_idx", columnList = "dateEnd", unique = false), //
})
public class Session extends AbstractSession implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static long STATISTIC_SAMPLE_SIZE = 500;

	@Column
	private String name;

	@Column
	@Enumerated(EnumType.STRING)
	private SessionType sessionType;

	@Column
	private String description;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateStart;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateEnd;

	@ManyToOne(optional = true)
	private Equipment equipment;

	@Column(columnDefinition="LONGVARCHAR")
	private String dataProviderUrl;

	@Column(columnDefinition="LONGVARCHAR")
	private String dataThumbnailUrl;

	@Column(columnDefinition="LONGVARCHAR")
	private String dataProviderHash;
	@Column
	private Date statCalculation;

	@Column
	private double statDistance;
	@Column
	private double statDistanceLocation;
	@Column
	private long statCalories;
	@Column
	private long statTimeTotal;
	@Column
	private long statTimeMoving;
	@Column
	private double statSpeedAverage;
	@Column
	private double statSpeedAverageMoving;
	@Column
	private double statSpeedMax;
	@Column
	private double statWheelPerimeter;

	@Column
	private long statCrankCadenceAverage;
	@Column
	private int statCrankCadenceMedian;
	@Column
	private long statCrankCadenceMax;
	@Column
	private long statCrankRevolutionTotal;
	@Column
	private int statWheelCadenceAverage;

	@Column
	private int statWheelCadenceMedian;
	@Column
	private int statWheelCadenceMax;
	@Column
	private long statWheelRevolutionTotal;
	@Column
	private long statPedometerCadenceAverage;

	@Column
	private long statPedometerCadenceMedian;
	@Column
	private long statPedometerCadenceMax;
	@Column
	private double statPedometerVerticalOscillationMedian;

	@Column
	private double statPedometerVerticalOscillationAverage;
	@Column
	private double statPedometerVerticalOscillationMax;
	@Column
	private long statPedometerGroundContactTimeMedian;

	@Column
	private long statPedometerGroundContactTimeAverage;
	@Column
	private long statPedometerGroundContactTimeMax;
	@Column
	private double statPedometerStride;

	@Column
	private long statPedometerTotal;
	@Column
	private long statHeartRateAverage;

	@Column
	private long statHeartRateMedian;
	@Column
	private long statHeartRateMax;
	@Column
	private long statHeartRateMin;
	@Column
	private long statHeartRateMaxMillis;
	@Column
	private long statHeartRateHardMillis;
	@Column
	private long statHeartRateModerateMillis;
	@Column
	private long statHeartRateLightMillis;
	@Column
	private long statHeartRateVeryLightMillis;
	@Column
	private long statHeartRateRestMillis;
	@Column
	private double statPowerAverage;

	@Column
	private double statPowerMedian;
	@Column
	private double statPowerMax;
	@Column
	private double statAltitudeMin;

	@Column
	private double statAltitudeMax;
	@Column
	private double statAltitudeCumulativeGain;
	@Column
	private double statAltitudeCumulativeLoss;
	@Column
	private double statGradeMin;

	@Column
	private double statGradeMax;
	@Column
	private double statLongitudeMin;

	@Column
	private double statLongitudeMax;
	@Column
	private double statLatitudeMin;
	@Column
	private double statLatitudeMax;
	@Column
	private String targetTimeFeedbackMode;

	@Column
	private long targetTime;
	@Column
	private String targetTimeFeedbackType;
	@Column
	private String targetDistanceFeedbackMode;
	@Column
	private double targetDistance;
	@Column
	private String targetDistanceFeedbackType;
	@Column
	private String targetSpeedPaceFeedbackMode;
	@Column
	private double targetSpeedPace;
	@Column
	private String targetSpeedPaceFeedbackType;
	@Column
	private String targetCadenceFeedbackMode;
	@Column
	private long targetCadence;
	@Column
	private String targetCadenceFeedbackType;
	@Column
	private String targetHeartRateFeedbackMode;
	@Column
	private long targetHeartRate;
	@Column
	private String targetHeartRateFeedbackType;
	@Column
	private String targetPowerFeedbackMode;
	@Column
	private long targetPower;
	@Column
	private String targetPowerFeedbackType;

	@Column
	public long businessVersion;

	public Session() {
	}

	public Session(String name, Date dateStart, UserProfile userProfile) {
		super(userProfile);
		this.name = name;
		this.dateStart = dateStart;
		
	}

	public String getDataProviderHash() {
		return dataProviderHash;
	}

	public String getDataProviderUrl() {
		return dataProviderUrl;
	}

	public String getDataThumbnailUrl() {
		return dataThumbnailUrl;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public String getDescription() {
		return description;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public long getBusinessVersion() {
		return businessVersion;
	}
	
	public void setBusinessVersion(long businessVersion) {
		this.businessVersion = businessVersion;
	}

	public String getName() {
		return name;
	}

	public SessionType getSessionType() {
		return sessionType;
	}

	public double getStatAltitudeCumulativeGain() {
		return statAltitudeCumulativeGain;
	}

	public double getStatAltitudeCumulativeLoss() {
		return statAltitudeCumulativeLoss;
	}

	public double getStatAltitudeMax() {
		return statAltitudeMax;
	}

	public double getStatAltitudeMin() {
		return statAltitudeMin;
	}

	public Date getStatCalculation() {
		return statCalculation;
	}

	public long getStatCalories() {
		return statCalories;
	}

	public long getStatCrankCadenceAverage() {
		return statCrankCadenceAverage;
	}

	public long getStatCrankCadenceMax() {
		return statCrankCadenceMax;
	}

	public int getStatCrankCadenceMedian() {
		return statCrankCadenceMedian;
	}

	public long getStatCrankRevolutionTotal() {
		return statCrankRevolutionTotal;
	}

	public double getStatDistance() {
		return statDistance;
	}

	public double getStatDistanceLocation() {
		return statDistanceLocation;
	}

	public double getStatGradeMax() {
		return statGradeMax;
	}

	public double getStatGradeMin() {
		return statGradeMin;
	}

	public long getStatHeartRateAverage() {
		return statHeartRateAverage;
	}

	public long getStatHeartRateHardMillis() {
		return statHeartRateHardMillis;
	}

	public long getStatHeartRateLightMillis() {
		return statHeartRateLightMillis;
	}

	public long getStatHeartRateMax() {
		return statHeartRateMax;
	}

	public long getStatHeartRateMaxMillis() {
		return statHeartRateMaxMillis;
	}

	public long getStatHeartRateMedian() {
		return statHeartRateMedian;
	}

	public long getStatHeartRateMin() {
		return statHeartRateMin;
	}

	public long getStatHeartRateModerateMillis() {
		return statHeartRateModerateMillis;
	}

	public long getStatHeartRateRestMillis() {
		return statHeartRateRestMillis;
	}

	public long getStatHeartRateVeryLightMillis() {
		return statHeartRateVeryLightMillis;
	}

	public double getStatLatitudeMax() {
		return statLatitudeMax;
	}

	public double getStatLatitudeMin() {
		return statLatitudeMin;
	}

	public double getStatLongitudeMax() {
		return statLongitudeMax;
	}

	public double getStatLongitudeMin() {
		return statLongitudeMin;
	}

	public long getStatPedometerCadenceAverage() {
		return statPedometerCadenceAverage;
	}

	public long getStatPedometerCadenceMax() {
		return statPedometerCadenceMax;
	}

	public long getStatPedometerCadenceMedian() {
		return statPedometerCadenceMedian;
	}

	public long getStatPedometerGroundContactTimeAverage() {
		return statPedometerGroundContactTimeAverage;
	}

	public long getStatPedometerGroundContactTimeMax() {
		return statPedometerGroundContactTimeMax;
	}

	public long getStatPedometerGroundContactTimeMedian() {
		return statPedometerGroundContactTimeMedian;
	}

	public double getStatPedometerStride() {
		return statPedometerStride;
	}

	public long getStatPedometerTotal() {
		return statPedometerTotal;
	}

	public double getStatPedometerVerticalOscillationAverage() {
		return statPedometerVerticalOscillationAverage;
	}

	public double getStatPedometerVerticalOscillationMax() {
		return statPedometerVerticalOscillationMax;
	}

	public double getStatPedometerVerticalOscillationMedian() {
		return statPedometerVerticalOscillationMedian;
	}

	public double getStatPowerAverage() {
		return statPowerAverage;
	}

	public double getStatPowerMax() {
		return statPowerMax;
	}

	public double getStatPowerMedian() {
		return statPowerMedian;
	}

	public double getStatSpeedAverage() {
		return statSpeedAverage;
	}

	public double getStatSpeedAverageMoving() {
		return statSpeedAverageMoving;
	}

	public double getStatSpeedMax() {
		return statSpeedMax;
	}

	public long getStatTimeMoving() {
		return statTimeMoving;
	}

	public long getStatTimeTotal() {
		return statTimeTotal;
	}

	public int getStatWheelCadenceAverage() {
		return statWheelCadenceAverage;
	}

	public int getStatWheelCadenceMax() {
		return statWheelCadenceMax;
	}

	public int getStatWheelCadenceMedian() {
		return statWheelCadenceMedian;
	}

	public double getStatWheelPerimeter() {
		return statWheelPerimeter;
	}

	public long getStatWheelRevolutionTotal() {
		return statWheelRevolutionTotal;
	}

	public long getTargetCadence() {
		return targetCadence;
	}

	public String getTargetCadenceFeedbackMode() {
		return targetCadenceFeedbackMode;
	}

	public String getTargetCadenceFeedbackType() {
		return targetCadenceFeedbackType;
	}

	public double getTargetDistance() {
		return targetDistance;
	}

	public String getTargetDistanceFeedbackMode() {
		return targetDistanceFeedbackMode;
	}

	public String getTargetDistanceFeedbackType() {
		return targetDistanceFeedbackType;
	}

	public long getTargetHeartRate() {
		return targetHeartRate;
	}

	public String getTargetHeartRateFeedbackMode() {
		return targetHeartRateFeedbackMode;
	}

	public String getTargetHeartRateFeedbackType() {
		return targetHeartRateFeedbackType;
	}

	public long getTargetPower() {
		return targetPower;
	}

	public String getTargetPowerFeedbackMode() {
		return targetPowerFeedbackMode;
	}

	public String getTargetPowerFeedbackType() {
		return targetPowerFeedbackType;
	}

	public double getTargetSpeedPace() {
		return targetSpeedPace;
	}

	public String getTargetSpeedPaceFeedbackMode() {
		return targetSpeedPaceFeedbackMode;
	}

	public String getTargetSpeedPaceFeedbackType() {
		return targetSpeedPaceFeedbackType;
	}

	public long getTargetTime() {
		return targetTime;
	}

	public String getTargetTimeFeedbackMode() {
		return targetTimeFeedbackMode;
	}

	public String getTargetTimeFeedbackType() {
		return targetTimeFeedbackType;
	}

	public long getTimeTotal() {
		return (statTimeTotal > 0 || dateEnd == null || dateStart == null) ? statTimeTotal : dateEnd.getTime() - dateStart.getTime();
	}

	public boolean isOpen() {
		return dateEnd == null;
	}

	public boolean isStarted() {
		return dateStart != null;
	}

	public boolean isStatAltitudeSet() {
		return statAltitudeMin != Double.MAX_VALUE;
	}

	public boolean isStatLatLongSet() {
		return statLatitudeMin != Double.MAX_VALUE;
	}

	public void setDataProviderHash(String dataProviderHash) {
		this.dataProviderHash = dataProviderHash;
	}

	public void setDataProviderUrl(String dataProviderUrl) {
		this.dataProviderUrl = dataProviderUrl;
	}

	public void setDataThumbnailUrl(String dataThumbnailUrl) {
		this.dataThumbnailUrl = dataThumbnailUrl;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSessionType(SessionType sessionType) {
		this.sessionType = sessionType;
	}

	public void setStatAltitudeCumulativeGain(double statAltitudeCumulativeGain) {
		this.statAltitudeCumulativeGain = statAltitudeCumulativeGain;
	}

	public void setStatAltitudeCumulativeLoss(double statAltitudeCumulativeLoss) {
		this.statAltitudeCumulativeLoss = statAltitudeCumulativeLoss;
	}

	public void setStatAltitudeMax(double statAltitudeMax) {
		this.statAltitudeMax = statAltitudeMax;
	}

	public void setStatAltitudeMin(double statAltitudeMin) {
		this.statAltitudeMin = statAltitudeMin;
	}

	public void setStatCalculation(Date statCalculation) {
		this.statCalculation = statCalculation;
	}

	public void setStatCalories(long statCalories) {
		this.statCalories = statCalories;
	}

	public void setStatCrankCadenceAverage(long statCrankCadenceAverage) {
		this.statCrankCadenceAverage = statCrankCadenceAverage;
	}

	public void setStatCrankCadenceMax(long statCrankCadenceMax) {
		this.statCrankCadenceMax = statCrankCadenceMax;
	}

	public void setStatCrankCadenceMedian(int statCrankCadenceMedian) {
		this.statCrankCadenceMedian = statCrankCadenceMedian;
	}

	public void setStatCrankRevolutionTotal(long statCrankRevolutionTotal) {
		this.statCrankRevolutionTotal = statCrankRevolutionTotal;
	}

	public void setStatDistance(double statDistance) {
		this.statDistance = statDistance;
	}

	public void setStatDistanceLocation(double statDistanceLocation) {
		this.statDistanceLocation = statDistanceLocation;
	}

	public void setStatGradeMax(double statGradeMax) {
		this.statGradeMax = statGradeMax;
	}

	public void setStatGradeMin(double statGradeMin) {
		this.statGradeMin = statGradeMin;
	}

	public void setStatHeartRateAverage(long statHeartRateAverage) {
		this.statHeartRateAverage = statHeartRateAverage;
	}

	public void setStatHeartRateHardMillis(long statHeartRateHardMillis) {
		this.statHeartRateHardMillis = statHeartRateHardMillis;
	}

	public void setStatHeartRateLightMillis(long statHeartRateLightMillis) {
		this.statHeartRateLightMillis = statHeartRateLightMillis;
	}

	public void setStatHeartRateMax(long statHeartRateMax) {
		this.statHeartRateMax = statHeartRateMax;
	}

	public void setStatHeartRateMaxMillis(long statHeartRateMaxMillis) {
		this.statHeartRateMaxMillis = statHeartRateMaxMillis;
	}

	public void setStatHeartRateMedian(long statHeartRateMedian) {
		this.statHeartRateMedian = statHeartRateMedian;
	}

	public void setStatHeartRateMin(long statHeartRateMin) {
		this.statHeartRateMin = statHeartRateMin;
	}

	public void setStatHeartRateModerateMillis(long statHeartRateModerateMillis) {
		this.statHeartRateModerateMillis = statHeartRateModerateMillis;
	}

	public void setStatHeartRateRestMillis(long statHeartRateRestMillis) {
		this.statHeartRateRestMillis = statHeartRateRestMillis;
	}

	public void setStatHeartRateVeryLightMillis(long statHeartRateVeryLightMillis) {
		this.statHeartRateVeryLightMillis = statHeartRateVeryLightMillis;
	}

	public void setStatLatitudeMax(double statLatitudeMax) {
		this.statLatitudeMax = statLatitudeMax;
	}

	public void setStatLatitudeMin(double statLatitudeMin) {
		this.statLatitudeMin = statLatitudeMin;
	}

	public void setStatLongitudeMax(double statLongitudeMax) {
		this.statLongitudeMax = statLongitudeMax;
	}

	public void setStatLongitudeMin(double statLongitudeMin) {
		this.statLongitudeMin = statLongitudeMin;
	}

	public void setStatPedometerCadenceAverage(long statPedometerCadenceAverage) {
		this.statPedometerCadenceAverage = statPedometerCadenceAverage;
	}

	public void setStatPedometerCadenceMax(long statPedometerCadenceMax) {
		this.statPedometerCadenceMax = statPedometerCadenceMax;
	}

	public void setStatPedometerCadenceMedian(long statPedometerCadenceMedian) {
		this.statPedometerCadenceMedian = statPedometerCadenceMedian;
	}

	public void setStatPedometerGroundContactTimeAverage(long statPedometerGroundContactTimeAverage) {
		this.statPedometerGroundContactTimeAverage = statPedometerGroundContactTimeAverage;
	}

	public void setStatPedometerGroundContactTimeMax(long statPedometerGroundContactTimeMax) {
		this.statPedometerGroundContactTimeMax = statPedometerGroundContactTimeMax;
	}

	public void setStatPedometerGroundContactTimeMedian(long statPedometerGroundContactTimeMedian) {
		this.statPedometerGroundContactTimeMedian = statPedometerGroundContactTimeMedian;
	}

	public void setStatPedometerStride(double statPedometerStride) {
		this.statPedometerStride = statPedometerStride;
	}

	public void setStatPedometerTotal(long statPedometerTotal) {
		this.statPedometerTotal = statPedometerTotal;
	}

	public void setStatPedometerVerticalOscillationAverage(double statPedometerVerticalOscillationAverage) {
		this.statPedometerVerticalOscillationAverage = statPedometerVerticalOscillationAverage;
	}

	public void setStatPedometerVerticalOscillationMax(double statPedometerVerticalOscillationMax) {
		this.statPedometerVerticalOscillationMax = statPedometerVerticalOscillationMax;
	}

	public void setStatPedometerVerticalOscillationMedian(double statPedometerVerticalOscillationMedian) {
		this.statPedometerVerticalOscillationMedian = statPedometerVerticalOscillationMedian;
	}

	public void setStatPowerAverage(double statPowerAverage) {
		this.statPowerAverage = statPowerAverage;
	}

	public void setStatPowerMax(double statPowerMax) {
		this.statPowerMax = statPowerMax;
	}

	public void setStatPowerMedian(double statPowerMedian) {
		this.statPowerMedian = statPowerMedian;
	}

	public void setStatSpeedAverage(double statSpeedAverage) {
		this.statSpeedAverage = statSpeedAverage;
	}

	public void setStatSpeedAverageMoving(double statSpeedAverageMoving) {
		this.statSpeedAverageMoving = statSpeedAverageMoving;
	}

	public void setStatSpeedMax(double statSpeedMax) {
		this.statSpeedMax = statSpeedMax;
	}

	public void setStatTimeMoving(long statTimeMoving) {
		this.statTimeMoving = statTimeMoving;
	}

	public void setStatTimeTotal(long statTimeTotal) {
		this.statTimeTotal = statTimeTotal;
	}

	public void setStatWheelCadenceAverage(int statWheelCadenceAverage) {
		this.statWheelCadenceAverage = statWheelCadenceAverage;
	}

	public void setStatWheelCadenceMax(int statWheelCadenceMax) {
		this.statWheelCadenceMax = statWheelCadenceMax;
	}

	public void setStatWheelCadenceMedian(int statWheelCadenceMedian) {
		this.statWheelCadenceMedian = statWheelCadenceMedian;
	}

	public void setStatWheelPerimeter(double statWheelPerimeter) {
		this.statWheelPerimeter = statWheelPerimeter;
	}

	public void setStatWheelRevolutionTotal(long statWheelRevolutionTotal) {
		this.statWheelRevolutionTotal = statWheelRevolutionTotal;
	}

	public void setTargetCadence(long targetCadence) {
		this.targetCadence = targetCadence;
	}

	public void setTargetCadenceFeedbackMode(String targetCadenceFeedbackMode) {
		this.targetCadenceFeedbackMode = targetCadenceFeedbackMode;
	}

	public void setTargetCadenceFeedbackType(String targetCadenceFeedbackType) {
		this.targetCadenceFeedbackType = targetCadenceFeedbackType;
	}

	public void setTargetDistance(double targetDistance) {
		this.targetDistance = targetDistance;
	}

	public void setTargetDistanceFeedbackMode(String targetDistanceFeedbackMode) {
		this.targetDistanceFeedbackMode = targetDistanceFeedbackMode;
	}

	public void setTargetDistanceFeedbackType(String targetDistanceFeedbackType) {
		this.targetDistanceFeedbackType = targetDistanceFeedbackType;
	}

	public void setTargetHeartRate(long targetHeartRate) {
		this.targetHeartRate = targetHeartRate;
	}

	public void setTargetHeartRateFeedbackMode(String targetHeartRateFeedbackMode) {
		this.targetHeartRateFeedbackMode = targetHeartRateFeedbackMode;
	}

	public void setTargetHeartRateFeedbackType(String targetHeartRateFeedbackType) {
		this.targetHeartRateFeedbackType = targetHeartRateFeedbackType;
	}

	public void setTargetPower(long targetPower) {
		this.targetPower = targetPower;
	}

	public void setTargetPowerFeedbackMode(String targetPowerFeedbackMode) {
		this.targetPowerFeedbackMode = targetPowerFeedbackMode;
	}

	public void setTargetPowerFeedbackType(String targetPowerFeedbackType) {
		this.targetPowerFeedbackType = targetPowerFeedbackType;
	}

	public void setTargetSpeedPace(double targetSpeedPace) {
		this.targetSpeedPace = targetSpeedPace;
	}

	public void setTargetSpeedPaceFeedbackMode(String targetSpeedPaceFeedbackMode) {
		this.targetSpeedPaceFeedbackMode = targetSpeedPaceFeedbackMode;
	}

	public void setTargetSpeedPaceFeedbackType(String targetSpeedPaceFeedbackType) {
		this.targetSpeedPaceFeedbackType = targetSpeedPaceFeedbackType;
	}

	public void setTargetTime(long targetTime) {
		this.targetTime = targetTime;
	}

	public void setTargetTimeFeedbackMode(String targetTimeFeedbackMode) {
		this.targetTimeFeedbackMode = targetTimeFeedbackMode;
	}

	public void setTargetTimeFeedbackType(String targetTimeFeedbackType) {
		this.targetTimeFeedbackType = targetTimeFeedbackType;
	}

	@Override
	public String toString() {
		return String.format("%s[%s, %s, %s]", getClass().getSimpleName(), getId(), name, dateStart);
	}

	public void ensureChildEntitiesRelations() {
			if (getTrackPeriods() != null){
				for (TrackPeriod trackPeriod : getTrackPeriods()) {
					trackPeriod.setSession(this);
					trackPeriod.ensureChildEntitiesRelations();
				}
			
		}
		
	}
}
