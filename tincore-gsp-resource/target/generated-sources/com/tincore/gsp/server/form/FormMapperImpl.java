package com.tincore.gsp.server.form;

import com.tincore.gsp.server.domain.BodyMetric;

import com.tincore.gsp.server.domain.Equipment;

import com.tincore.gsp.server.domain.Nutrition;

import com.tincore.gsp.server.domain.Session;

import com.tincore.gsp.server.domain.TrackPeriod;

import com.tincore.gsp.server.domain.TrackPoint;

import com.tincore.gsp.server.domain.UserProfile;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Generated;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;

import org.springframework.stereotype.Component;

@Generated(

    value = "org.mapstruct.ap.MappingProcessor",

    date = "2016-09-26T21:18:09+0200",

    comments = "version: 1.1.0.CR1, compiler: javac, environment: Java 1.8.0_101 (Oracle Corporation)"

)

@Component

public class FormMapperImpl implements FormMapper {

    @Autowired

    private FormMapperUtil formMapperUtil;

    @Override

    public UserProfileForm toUserProfileForm(UserProfile personalProfile) {

        if ( personalProfile == null ) {

            return null;
        }

        UserProfileForm userProfileForm = new UserProfileForm();

        userProfileForm.setBirthDate( personalProfile.getBirthDate() );

        userProfileForm.setCalorieDayTarget( personalProfile.getCalorieDayTarget() );

        userProfileForm.setCalorieInDayTarget( personalProfile.getCalorieInDayTarget() );

        userProfileForm.setGender( personalProfile.getGender() );

        userProfileForm.setHeartRateMax( personalProfile.getHeartRateMax() );

        userProfileForm.setHeartRateRest( personalProfile.getHeartRateRest() );

        userProfileForm.setHeight( personalProfile.getHeight() );

        userProfileForm.setHydrationDayTarget( personalProfile.getHydrationDayTarget() );

        userProfileForm.setPedometerDayDistanceTarget( personalProfile.getPedometerDayDistanceTarget() );

        userProfileForm.setPedometerDayStepTarget( personalProfile.getPedometerDayStepTarget() );

        userProfileForm.setSleepDayTarget( personalProfile.getSleepDayTarget() );

        userProfileForm.setStrideLength( personalProfile.getStrideLength() );

        userProfileForm.setStrideLengthRun( personalProfile.getStrideLengthRun() );

        userProfileForm.setVolumeOxygenMax( personalProfile.getVolumeOxygenMax() );

        return userProfileForm;
    }

    @Override

    public void update(UserProfileForm userProfileForm, UserProfile userProfile) {

        if ( userProfileForm == null ) {

            return;
        }

        userProfile.setBirthDate( userProfileForm.getBirthDate() );

        userProfile.setCalorieDayTarget( userProfileForm.getCalorieDayTarget() );

        userProfile.setCalorieInDayTarget( userProfileForm.getCalorieInDayTarget() );

        userProfile.setGender( userProfileForm.getGender() );

        userProfile.setHeartRateMax( userProfileForm.getHeartRateMax() );

        userProfile.setHeartRateRest( userProfileForm.getHeartRateRest() );

        userProfile.setHeight( userProfileForm.getHeight() );

        userProfile.setHydrationDayTarget( userProfileForm.getHydrationDayTarget() );

        userProfile.setPedometerDayDistanceTarget( userProfileForm.getPedometerDayDistanceTarget() );

        userProfile.setPedometerDayStepTarget( userProfileForm.getPedometerDayStepTarget() );

        userProfile.setSleepDayTarget( userProfileForm.getSleepDayTarget() );

        userProfile.setStrideLength( userProfileForm.getStrideLength() );

        userProfile.setStrideLengthRun( userProfileForm.getStrideLengthRun() );

        userProfile.setVolumeOxygenMax( userProfileForm.getVolumeOxygenMax() );
    }

    @Override

    public EquipmentForm toEquipmentForm(Equipment equipment) {

        if ( equipment == null ) {

            return null;
        }

        EquipmentForm equipmentForm = new EquipmentForm();

        equipmentForm.setBusinessVersion( equipment.getBusinessVersion() );

        equipmentForm.setId( equipment.getId() );

        equipmentForm.setName( equipment.getName() );

        equipmentForm.setWeight( equipment.getWeight() );

        equipmentForm.setWheelPerimeter( equipment.getWheelPerimeter() );

        return equipmentForm;
    }

    @Override

    public Equipment toEquipment(EquipmentForm equipmentForm, UserProfile userProfile) {

        if ( equipmentForm == null && userProfile == null ) {

            return null;
        }

        Equipment equipment = new Equipment();

        if ( equipmentForm != null ) {

            equipment.setBusinessVersion( equipmentForm.getBusinessVersion() );

            equipment.setName( equipmentForm.getName() );

            equipment.setWeight( equipmentForm.getWeight() );

            equipment.setWheelPerimeter( equipmentForm.getWheelPerimeter() );
        }

        if ( userProfile != null ) {

            equipment.setUserProfile( userProfile );
        }

        return equipment;
    }

    @Override

    public Equipment toEquipment(EquipmentForm equipmentForm) {

        if ( equipmentForm == null ) {

            return null;
        }

        Equipment equipment = new Equipment();

        equipment.setBusinessVersion( equipmentForm.getBusinessVersion() );

        equipment.setName( equipmentForm.getName() );

        equipment.setWeight( equipmentForm.getWeight() );

        equipment.setWheelPerimeter( equipmentForm.getWheelPerimeter() );

        return equipment;
    }

    @Override

    public PageForm<EquipmentForm> toEquipmentForms(Page<Equipment> equipment) {

        if ( equipment == null ) {

            return null;
        }

        PageForm<EquipmentForm> pageForm = new PageForm<EquipmentForm> ();

        for ( Equipment equipment_ : equipment ) {

            pageForm.add( toEquipmentForm( equipment_ ) );
        }

        formMapperUtil.onAfterPage( equipment, pageForm );

        return pageForm;
    }

    @Override

    public List<EquipmentForm> toEquipmentForms(List<Equipment> equipment) {

        if ( equipment == null ) {

            return null;
        }

        List<EquipmentForm> list = new ArrayList<EquipmentForm>();

        for ( Equipment equipment_ : equipment ) {

            list.add( toEquipmentForm( equipment_ ) );
        }

        return list;
    }

    @Override

    public void update(EquipmentForm equipmentForm, Equipment equipment) {

        if ( equipmentForm == null ) {

            return;
        }

        equipment.setBusinessVersion( equipmentForm.getBusinessVersion() );

        equipment.setName( equipmentForm.getName() );

        equipment.setWeight( equipmentForm.getWeight() );

        equipment.setWheelPerimeter( equipmentForm.getWheelPerimeter() );
    }

    @Override

    public BodyMetricForm toBodyMetricForm(BodyMetric bodyMetric) {

        if ( bodyMetric == null ) {

            return null;
        }

        BodyMetricForm bodyMetricForm = new BodyMetricForm();

        bodyMetricForm.setBusinessVersion( bodyMetric.getBusinessVersion() );

        bodyMetricForm.setArmPerimeter( bodyMetric.getArmPerimeter() );

        bodyMetricForm.setCalfPerimeter( bodyMetric.getCalfPerimeter() );

        bodyMetricForm.setCalliperBackLength( bodyMetric.getCalliperBackLength() );

        bodyMetricForm.setCalliperChestLength( bodyMetric.getCalliperChestLength() );

        bodyMetricForm.setCalliperThightLength( bodyMetric.getCalliperThightLength() );

        bodyMetricForm.setCalliperTricepsLength( bodyMetric.getCalliperTricepsLength() );

        bodyMetricForm.setCalliperWaistLength( bodyMetric.getCalliperWaistLength() );

        bodyMetricForm.setChestPerimeter( bodyMetric.getChestPerimeter() );

        bodyMetricForm.setDate( bodyMetric.getDate() );

        bodyMetricForm.setForearmPerimeter( bodyMetric.getForearmPerimeter() );

        bodyMetricForm.setHipPerimeter( bodyMetric.getHipPerimeter() );

        bodyMetricForm.setId( bodyMetric.getId() );

        bodyMetricForm.setNeckPerimeter( bodyMetric.getNeckPerimeter() );

        bodyMetricForm.setThightPerimeter( bodyMetric.getThightPerimeter() );

        bodyMetricForm.setWaistPerimeter( bodyMetric.getWaistPerimeter() );

        bodyMetricForm.setWeight( bodyMetric.getWeight() );

        return bodyMetricForm;
    }

    @Override

    public BodyMetric toBodyMetric(BodyMetricForm bodyMetricForm, UserProfile userProfile) {

        if ( bodyMetricForm == null && userProfile == null ) {

            return null;
        }

        BodyMetric bodyMetric = new BodyMetric();

        if ( bodyMetricForm != null ) {

            bodyMetric.setBusinessVersion( bodyMetricForm.getBusinessVersion() );

            bodyMetric.setArmPerimeter( bodyMetricForm.getArmPerimeter() );

            bodyMetric.setCalfPerimeter( bodyMetricForm.getCalfPerimeter() );

            bodyMetric.setCalliperBackLength( bodyMetricForm.getCalliperBackLength() );

            bodyMetric.setCalliperChestLength( bodyMetricForm.getCalliperChestLength() );

            bodyMetric.setCalliperThightLength( bodyMetricForm.getCalliperThightLength() );

            bodyMetric.setCalliperTricepsLength( bodyMetricForm.getCalliperTricepsLength() );

            bodyMetric.setCalliperWaistLength( bodyMetricForm.getCalliperWaistLength() );

            bodyMetric.setChestPerimeter( bodyMetricForm.getChestPerimeter() );

            bodyMetric.setDate( bodyMetricForm.getDate() );

            bodyMetric.setForearmPerimeter( bodyMetricForm.getForearmPerimeter() );

            bodyMetric.setHipPerimeter( bodyMetricForm.getHipPerimeter() );

            bodyMetric.setNeckPerimeter( bodyMetricForm.getNeckPerimeter() );

            bodyMetric.setThightPerimeter( bodyMetricForm.getThightPerimeter() );

            bodyMetric.setWaistPerimeter( bodyMetricForm.getWaistPerimeter() );

            bodyMetric.setWeight( bodyMetricForm.getWeight() );
        }

        if ( userProfile != null ) {

            bodyMetric.setUserProfile( userProfile );
        }

        return bodyMetric;
    }

    @Override

    public PageForm<BodyMetricForm> toBodyMetricForms(Page<BodyMetric> bodyMetric) {

        if ( bodyMetric == null ) {

            return null;
        }

        PageForm<BodyMetricForm> pageForm = new PageForm<BodyMetricForm> ();

        for ( BodyMetric bodyMetric_ : bodyMetric ) {

            pageForm.add( toBodyMetricForm( bodyMetric_ ) );
        }

        formMapperUtil.onAfterPage( bodyMetric, pageForm );

        return pageForm;
    }

    @Override

    public List<BodyMetricForm> toBodyMetricForms(List<BodyMetric> bodyMetric) {

        if ( bodyMetric == null ) {

            return null;
        }

        List<BodyMetricForm> list = new ArrayList<BodyMetricForm>();

        for ( BodyMetric bodyMetric_ : bodyMetric ) {

            list.add( toBodyMetricForm( bodyMetric_ ) );
        }

        return list;
    }

    @Override

    public void update(BodyMetricForm bodyMetricForm, BodyMetric bodyMetric) {

        if ( bodyMetricForm == null ) {

            return;
        }

        bodyMetric.setBusinessVersion( bodyMetricForm.getBusinessVersion() );

        bodyMetric.setArmPerimeter( bodyMetricForm.getArmPerimeter() );

        bodyMetric.setCalfPerimeter( bodyMetricForm.getCalfPerimeter() );

        bodyMetric.setCalliperBackLength( bodyMetricForm.getCalliperBackLength() );

        bodyMetric.setCalliperChestLength( bodyMetricForm.getCalliperChestLength() );

        bodyMetric.setCalliperThightLength( bodyMetricForm.getCalliperThightLength() );

        bodyMetric.setCalliperTricepsLength( bodyMetricForm.getCalliperTricepsLength() );

        bodyMetric.setCalliperWaistLength( bodyMetricForm.getCalliperWaistLength() );

        bodyMetric.setChestPerimeter( bodyMetricForm.getChestPerimeter() );

        bodyMetric.setDate( bodyMetricForm.getDate() );

        bodyMetric.setForearmPerimeter( bodyMetricForm.getForearmPerimeter() );

        bodyMetric.setHipPerimeter( bodyMetricForm.getHipPerimeter() );

        bodyMetric.setNeckPerimeter( bodyMetricForm.getNeckPerimeter() );

        bodyMetric.setThightPerimeter( bodyMetricForm.getThightPerimeter() );

        bodyMetric.setWaistPerimeter( bodyMetricForm.getWaistPerimeter() );

        bodyMetric.setWeight( bodyMetricForm.getWeight() );
    }

    @Override

    public NutritionForm toNutritionForm(Nutrition nutrition) {

        if ( nutrition == null ) {

            return null;
        }

        NutritionForm nutritionForm = new NutritionForm();

        nutritionForm.setBusinessVersion( nutrition.getBusinessVersion() );

        nutritionForm.setCalories( nutrition.getCalories() );

        nutritionForm.setCarbohydrates( nutrition.getCarbohydrates() );

        nutritionForm.setCode( nutrition.getCode() );

        nutritionForm.setDate( nutrition.getDate() );

        nutritionForm.setDescription( nutrition.getDescription() );

        nutritionForm.setFat( nutrition.getFat() );

        nutritionForm.setFiber( nutrition.getFiber() );

        nutritionForm.setId( nutrition.getId() );

        nutritionForm.setProtein( nutrition.getProtein() );

        nutritionForm.setSodium( nutrition.getSodium() );

        nutritionForm.setWater( nutrition.getWater() );

        return nutritionForm;
    }

    @Override

    public Nutrition toNutrition(NutritionForm nutritionForm, UserProfile userProfile) {

        if ( nutritionForm == null && userProfile == null ) {

            return null;
        }

        Nutrition nutrition = new Nutrition();

        if ( nutritionForm != null ) {

            nutrition.setBusinessVersion( nutritionForm.getBusinessVersion() );

            nutrition.setCalories( nutritionForm.getCalories() );

            nutrition.setCarbohydrates( nutritionForm.getCarbohydrates() );

            nutrition.setCode( nutritionForm.getCode() );

            nutrition.setDate( nutritionForm.getDate() );

            nutrition.setDescription( nutritionForm.getDescription() );

            nutrition.setFat( nutritionForm.getFat() );

            nutrition.setFiber( nutritionForm.getFiber() );

            nutrition.setProtein( nutritionForm.getProtein() );

            nutrition.setSodium( nutritionForm.getSodium() );

            nutrition.setWater( nutritionForm.getWater() );
        }

        if ( userProfile != null ) {

            nutrition.setUserProfile( userProfile );
        }

        return nutrition;
    }

    @Override

    public PageForm<NutritionForm> toNutritionForms(Page<Nutrition> nutrition) {

        if ( nutrition == null ) {

            return null;
        }

        PageForm<NutritionForm> pageForm = new PageForm<NutritionForm> ();

        for ( Nutrition nutrition_ : nutrition ) {

            pageForm.add( toNutritionForm( nutrition_ ) );
        }

        formMapperUtil.onAfterPage( nutrition, pageForm );

        return pageForm;
    }

    @Override

    public List<NutritionForm> toNutritionForms(List<Nutrition> nutrition) {

        if ( nutrition == null ) {

            return null;
        }

        List<NutritionForm> list = new ArrayList<NutritionForm>();

        for ( Nutrition nutrition_ : nutrition ) {

            list.add( toNutritionForm( nutrition_ ) );
        }

        return list;
    }

    @Override

    public void update(NutritionForm nutritionForm, Nutrition nutrition) {

        if ( nutritionForm == null ) {

            return;
        }

        nutrition.setBusinessVersion( nutritionForm.getBusinessVersion() );

        nutrition.setCalories( nutritionForm.getCalories() );

        nutrition.setCarbohydrates( nutritionForm.getCarbohydrates() );

        nutrition.setCode( nutritionForm.getCode() );

        nutrition.setDate( nutritionForm.getDate() );

        nutrition.setDescription( nutritionForm.getDescription() );

        nutrition.setFat( nutritionForm.getFat() );

        nutrition.setFiber( nutritionForm.getFiber() );

        nutrition.setProtein( nutritionForm.getProtein() );

        nutrition.setSodium( nutritionForm.getSodium() );

        nutrition.setWater( nutritionForm.getWater() );
    }

    @Override

    public SessionForm toSessionForm(Session session) {

        if ( session == null ) {

            return null;
        }

        SessionForm sessionForm = new SessionForm();

        sessionForm.setBusinessVersion( session.getBusinessVersion() );

        sessionForm.setDataProviderHash( session.getDataProviderHash() );

        sessionForm.setDataProviderUrl( session.getDataProviderUrl() );

        sessionForm.setDataThumbnailUrl( session.getDataThumbnailUrl() );

        sessionForm.setDateEnd( session.getDateEnd() );

        sessionForm.setDateStart( session.getDateStart() );

        sessionForm.setDescription( session.getDescription() );

        sessionForm.setEquipment( formMapperUtil.map( session.getEquipment() ) );

        sessionForm.setId( session.getId() );

        sessionForm.setName( session.getName() );

        sessionForm.setSessionType( session.getSessionType() );

        sessionForm.setStatAltitudeCumulativeGain( session.getStatAltitudeCumulativeGain() );

        sessionForm.setStatAltitudeCumulativeLoss( session.getStatAltitudeCumulativeLoss() );

        sessionForm.setStatAltitudeMax( session.getStatAltitudeMax() );

        sessionForm.setStatAltitudeMin( session.getStatAltitudeMin() );

        sessionForm.setStatCalculation( session.getStatCalculation() );

        sessionForm.setStatCalories( session.getStatCalories() );

        sessionForm.setStatCrankCadenceAverage( session.getStatCrankCadenceAverage() );

        sessionForm.setStatCrankCadenceMax( session.getStatCrankCadenceMax() );

        sessionForm.setStatCrankCadenceMedian( session.getStatCrankCadenceMedian() );

        sessionForm.setStatCrankRevolutionTotal( session.getStatCrankRevolutionTotal() );

        sessionForm.setStatDistance( session.getStatDistance() );

        sessionForm.setStatDistanceLocation( session.getStatDistanceLocation() );

        sessionForm.setStatGradeMax( session.getStatGradeMax() );

        sessionForm.setStatGradeMin( session.getStatGradeMin() );

        sessionForm.setStatHeartRateAverage( session.getStatHeartRateAverage() );

        sessionForm.setStatHeartRateHardMillis( session.getStatHeartRateHardMillis() );

        sessionForm.setStatHeartRateLightMillis( session.getStatHeartRateLightMillis() );

        sessionForm.setStatHeartRateMax( session.getStatHeartRateMax() );

        sessionForm.setStatHeartRateMaxMillis( session.getStatHeartRateMaxMillis() );

        sessionForm.setStatHeartRateMedian( session.getStatHeartRateMedian() );

        sessionForm.setStatHeartRateMin( session.getStatHeartRateMin() );

        sessionForm.setStatHeartRateModerateMillis( session.getStatHeartRateModerateMillis() );

        sessionForm.setStatHeartRateRestMillis( session.getStatHeartRateRestMillis() );

        sessionForm.setStatHeartRateVeryLightMillis( session.getStatHeartRateVeryLightMillis() );

        sessionForm.setStatLatitudeMax( session.getStatLatitudeMax() );

        sessionForm.setStatLatitudeMin( session.getStatLatitudeMin() );

        sessionForm.setStatLongitudeMax( session.getStatLongitudeMax() );

        sessionForm.setStatLongitudeMin( session.getStatLongitudeMin() );

        sessionForm.setStatPedometerCadenceAverage( session.getStatPedometerCadenceAverage() );

        sessionForm.setStatPedometerCadenceMax( session.getStatPedometerCadenceMax() );

        sessionForm.setStatPedometerCadenceMedian( session.getStatPedometerCadenceMedian() );

        sessionForm.setStatPedometerGroundContactTimeAverage( session.getStatPedometerGroundContactTimeAverage() );

        sessionForm.setStatPedometerGroundContactTimeMax( session.getStatPedometerGroundContactTimeMax() );

        sessionForm.setStatPedometerGroundContactTimeMedian( session.getStatPedometerGroundContactTimeMedian() );

        sessionForm.setStatPedometerStride( session.getStatPedometerStride() );

        sessionForm.setStatPedometerTotal( session.getStatPedometerTotal() );

        sessionForm.setStatPedometerVerticalOscillationAverage( session.getStatPedometerVerticalOscillationAverage() );

        sessionForm.setStatPedometerVerticalOscillationMax( session.getStatPedometerVerticalOscillationMax() );

        sessionForm.setStatPedometerVerticalOscillationMedian( session.getStatPedometerVerticalOscillationMedian() );

        sessionForm.setStatPowerAverage( session.getStatPowerAverage() );

        sessionForm.setStatPowerMax( session.getStatPowerMax() );

        sessionForm.setStatPowerMedian( session.getStatPowerMedian() );

        sessionForm.setStatSpeedAverage( session.getStatSpeedAverage() );

        sessionForm.setStatSpeedAverageMoving( session.getStatSpeedAverageMoving() );

        sessionForm.setStatSpeedMax( session.getStatSpeedMax() );

        sessionForm.setStatTimeMoving( session.getStatTimeMoving() );

        sessionForm.setStatTimeTotal( session.getStatTimeTotal() );

        sessionForm.setStatWheelCadenceAverage( session.getStatWheelCadenceAverage() );

        sessionForm.setStatWheelCadenceMax( session.getStatWheelCadenceMax() );

        sessionForm.setStatWheelCadenceMedian( session.getStatWheelCadenceMedian() );

        sessionForm.setStatWheelPerimeter( session.getStatWheelPerimeter() );

        sessionForm.setStatWheelRevolutionTotal( session.getStatWheelRevolutionTotal() );

        sessionForm.setTargetCadence( session.getTargetCadence() );

        sessionForm.setTargetCadenceFeedbackMode( session.getTargetCadenceFeedbackMode() );

        sessionForm.setTargetCadenceFeedbackType( session.getTargetCadenceFeedbackType() );

        sessionForm.setTargetDistance( session.getTargetDistance() );

        sessionForm.setTargetDistanceFeedbackMode( session.getTargetDistanceFeedbackMode() );

        sessionForm.setTargetDistanceFeedbackType( session.getTargetDistanceFeedbackType() );

        sessionForm.setTargetHeartRate( session.getTargetHeartRate() );

        sessionForm.setTargetHeartRateFeedbackMode( session.getTargetHeartRateFeedbackMode() );

        sessionForm.setTargetHeartRateFeedbackType( session.getTargetHeartRateFeedbackType() );

        sessionForm.setTargetPower( session.getTargetPower() );

        sessionForm.setTargetPowerFeedbackMode( session.getTargetPowerFeedbackMode() );

        sessionForm.setTargetPowerFeedbackType( session.getTargetPowerFeedbackType() );

        sessionForm.setTargetSpeedPace( session.getTargetSpeedPace() );

        sessionForm.setTargetSpeedPaceFeedbackMode( session.getTargetSpeedPaceFeedbackMode() );

        sessionForm.setTargetSpeedPaceFeedbackType( session.getTargetSpeedPaceFeedbackType() );

        sessionForm.setTargetTime( session.getTargetTime() );

        sessionForm.setTargetTimeFeedbackMode( session.getTargetTimeFeedbackMode() );

        sessionForm.setTargetTimeFeedbackType( session.getTargetTimeFeedbackType() );

        return sessionForm;
    }

    @Override

    public Session toSession(SessionForm sessionForm, UserProfile userProfile) {

        if ( sessionForm == null && userProfile == null ) {

            return null;
        }

        Session session = new Session();

        if ( sessionForm != null ) {

            session.setBusinessVersion( sessionForm.getBusinessVersion() );

            session.setDataProviderHash( sessionForm.getDataProviderHash() );

            session.setDataProviderUrl( sessionForm.getDataProviderUrl() );

            session.setDataThumbnailUrl( sessionForm.getDataThumbnailUrl() );

            session.setDateEnd( sessionForm.getDateEnd() );

            session.setDateStart( sessionForm.getDateStart() );

            session.setDescription( sessionForm.getDescription() );

            session.setName( sessionForm.getName() );

            session.setSessionType( sessionForm.getSessionType() );

            session.setStatAltitudeCumulativeGain( sessionForm.getStatAltitudeCumulativeGain() );

            session.setStatAltitudeCumulativeLoss( sessionForm.getStatAltitudeCumulativeLoss() );

            session.setStatAltitudeMax( sessionForm.getStatAltitudeMax() );

            session.setStatAltitudeMin( sessionForm.getStatAltitudeMin() );

            session.setStatCalculation( sessionForm.getStatCalculation() );

            session.setStatCalories( sessionForm.getStatCalories() );

            session.setStatCrankCadenceAverage( sessionForm.getStatCrankCadenceAverage() );

            session.setStatCrankCadenceMax( sessionForm.getStatCrankCadenceMax() );

            session.setStatCrankCadenceMedian( sessionForm.getStatCrankCadenceMedian() );

            session.setStatCrankRevolutionTotal( sessionForm.getStatCrankRevolutionTotal() );

            session.setStatDistance( sessionForm.getStatDistance() );

            session.setStatDistanceLocation( sessionForm.getStatDistanceLocation() );

            session.setStatGradeMax( sessionForm.getStatGradeMax() );

            session.setStatGradeMin( sessionForm.getStatGradeMin() );

            session.setStatHeartRateAverage( sessionForm.getStatHeartRateAverage() );

            session.setStatHeartRateHardMillis( sessionForm.getStatHeartRateHardMillis() );

            session.setStatHeartRateLightMillis( sessionForm.getStatHeartRateLightMillis() );

            session.setStatHeartRateMax( sessionForm.getStatHeartRateMax() );

            session.setStatHeartRateMaxMillis( sessionForm.getStatHeartRateMaxMillis() );

            session.setStatHeartRateMedian( sessionForm.getStatHeartRateMedian() );

            session.setStatHeartRateMin( sessionForm.getStatHeartRateMin() );

            session.setStatHeartRateModerateMillis( sessionForm.getStatHeartRateModerateMillis() );

            session.setStatHeartRateRestMillis( sessionForm.getStatHeartRateRestMillis() );

            session.setStatHeartRateVeryLightMillis( sessionForm.getStatHeartRateVeryLightMillis() );

            session.setStatLatitudeMax( sessionForm.getStatLatitudeMax() );

            session.setStatLatitudeMin( sessionForm.getStatLatitudeMin() );

            session.setStatLongitudeMax( sessionForm.getStatLongitudeMax() );

            session.setStatLongitudeMin( sessionForm.getStatLongitudeMin() );

            session.setStatPedometerCadenceAverage( sessionForm.getStatPedometerCadenceAverage() );

            session.setStatPedometerCadenceMax( sessionForm.getStatPedometerCadenceMax() );

            session.setStatPedometerCadenceMedian( sessionForm.getStatPedometerCadenceMedian() );

            session.setStatPedometerGroundContactTimeAverage( sessionForm.getStatPedometerGroundContactTimeAverage() );

            session.setStatPedometerGroundContactTimeMax( sessionForm.getStatPedometerGroundContactTimeMax() );

            session.setStatPedometerGroundContactTimeMedian( sessionForm.getStatPedometerGroundContactTimeMedian() );

            session.setStatPedometerStride( sessionForm.getStatPedometerStride() );

            session.setStatPedometerTotal( sessionForm.getStatPedometerTotal() );

            session.setStatPedometerVerticalOscillationAverage( sessionForm.getStatPedometerVerticalOscillationAverage() );

            session.setStatPedometerVerticalOscillationMax( sessionForm.getStatPedometerVerticalOscillationMax() );

            session.setStatPedometerVerticalOscillationMedian( sessionForm.getStatPedometerVerticalOscillationMedian() );

            session.setStatPowerAverage( sessionForm.getStatPowerAverage() );

            session.setStatPowerMax( sessionForm.getStatPowerMax() );

            session.setStatPowerMedian( sessionForm.getStatPowerMedian() );

            session.setStatSpeedAverage( sessionForm.getStatSpeedAverage() );

            session.setStatSpeedAverageMoving( sessionForm.getStatSpeedAverageMoving() );

            session.setStatSpeedMax( sessionForm.getStatSpeedMax() );

            session.setStatTimeMoving( sessionForm.getStatTimeMoving() );

            session.setStatTimeTotal( sessionForm.getStatTimeTotal() );

            session.setStatWheelCadenceAverage( sessionForm.getStatWheelCadenceAverage() );

            session.setStatWheelCadenceMax( sessionForm.getStatWheelCadenceMax() );

            session.setStatWheelCadenceMedian( sessionForm.getStatWheelCadenceMedian() );

            session.setStatWheelPerimeter( sessionForm.getStatWheelPerimeter() );

            session.setStatWheelRevolutionTotal( sessionForm.getStatWheelRevolutionTotal() );

            session.setTargetCadence( sessionForm.getTargetCadence() );

            session.setTargetCadenceFeedbackMode( sessionForm.getTargetCadenceFeedbackMode() );

            session.setTargetCadenceFeedbackType( sessionForm.getTargetCadenceFeedbackType() );

            session.setTargetDistance( sessionForm.getTargetDistance() );

            session.setTargetDistanceFeedbackMode( sessionForm.getTargetDistanceFeedbackMode() );

            session.setTargetDistanceFeedbackType( sessionForm.getTargetDistanceFeedbackType() );

            session.setTargetHeartRate( sessionForm.getTargetHeartRate() );

            session.setTargetHeartRateFeedbackMode( sessionForm.getTargetHeartRateFeedbackMode() );

            session.setTargetHeartRateFeedbackType( sessionForm.getTargetHeartRateFeedbackType() );

            session.setTargetPower( sessionForm.getTargetPower() );

            session.setTargetPowerFeedbackMode( sessionForm.getTargetPowerFeedbackMode() );

            session.setTargetPowerFeedbackType( sessionForm.getTargetPowerFeedbackType() );

            session.setTargetSpeedPace( sessionForm.getTargetSpeedPace() );

            session.setTargetSpeedPaceFeedbackMode( sessionForm.getTargetSpeedPaceFeedbackMode() );

            session.setTargetSpeedPaceFeedbackType( sessionForm.getTargetSpeedPaceFeedbackType() );

            session.setTargetTime( sessionForm.getTargetTime() );

            session.setTargetTimeFeedbackMode( sessionForm.getTargetTimeFeedbackMode() );

            session.setTargetTimeFeedbackType( sessionForm.getTargetTimeFeedbackType() );
        }

        if ( userProfile != null ) {

            session.setUserProfile( userProfile );
        }

        return session;
    }

    @Override

    public PageForm<SessionForm> toSessionForms(Page<Session> session) {

        if ( session == null ) {

            return null;
        }

        PageForm<SessionForm> pageForm = new PageForm<SessionForm> ();

        for ( Session session_ : session ) {

            pageForm.add( toSessionForm( session_ ) );
        }

        formMapperUtil.onAfterPage( session, pageForm );

        return pageForm;
    }

    @Override

    public List<SessionForm> toSessionForms(List<Session> session) {

        if ( session == null ) {

            return null;
        }

        List<SessionForm> list = new ArrayList<SessionForm>();

        for ( Session session_ : session ) {

            list.add( toSessionForm( session_ ) );
        }

        return list;
    }

    @Override

    public void update(SessionForm sessionForm, Session session) {

        if ( sessionForm == null ) {

            return;
        }

        session.setBusinessVersion( sessionForm.getBusinessVersion() );

        session.setDataProviderHash( sessionForm.getDataProviderHash() );

        session.setDataProviderUrl( sessionForm.getDataProviderUrl() );

        session.setDataThumbnailUrl( sessionForm.getDataThumbnailUrl() );

        session.setDateEnd( sessionForm.getDateEnd() );

        session.setDateStart( sessionForm.getDateStart() );

        session.setDescription( sessionForm.getDescription() );

        session.setEquipment( formMapperUtil.map( sessionForm.getEquipment() ) );

        session.setName( sessionForm.getName() );

        session.setSessionType( sessionForm.getSessionType() );

        session.setStatAltitudeCumulativeGain( sessionForm.getStatAltitudeCumulativeGain() );

        session.setStatAltitudeCumulativeLoss( sessionForm.getStatAltitudeCumulativeLoss() );

        session.setStatAltitudeMax( sessionForm.getStatAltitudeMax() );

        session.setStatAltitudeMin( sessionForm.getStatAltitudeMin() );

        session.setStatCalculation( sessionForm.getStatCalculation() );

        session.setStatCalories( sessionForm.getStatCalories() );

        session.setStatCrankCadenceAverage( sessionForm.getStatCrankCadenceAverage() );

        session.setStatCrankCadenceMax( sessionForm.getStatCrankCadenceMax() );

        session.setStatCrankCadenceMedian( sessionForm.getStatCrankCadenceMedian() );

        session.setStatCrankRevolutionTotal( sessionForm.getStatCrankRevolutionTotal() );

        session.setStatDistance( sessionForm.getStatDistance() );

        session.setStatDistanceLocation( sessionForm.getStatDistanceLocation() );

        session.setStatGradeMax( sessionForm.getStatGradeMax() );

        session.setStatGradeMin( sessionForm.getStatGradeMin() );

        session.setStatHeartRateAverage( sessionForm.getStatHeartRateAverage() );

        session.setStatHeartRateHardMillis( sessionForm.getStatHeartRateHardMillis() );

        session.setStatHeartRateLightMillis( sessionForm.getStatHeartRateLightMillis() );

        session.setStatHeartRateMax( sessionForm.getStatHeartRateMax() );

        session.setStatHeartRateMaxMillis( sessionForm.getStatHeartRateMaxMillis() );

        session.setStatHeartRateMedian( sessionForm.getStatHeartRateMedian() );

        session.setStatHeartRateMin( sessionForm.getStatHeartRateMin() );

        session.setStatHeartRateModerateMillis( sessionForm.getStatHeartRateModerateMillis() );

        session.setStatHeartRateRestMillis( sessionForm.getStatHeartRateRestMillis() );

        session.setStatHeartRateVeryLightMillis( sessionForm.getStatHeartRateVeryLightMillis() );

        session.setStatLatitudeMax( sessionForm.getStatLatitudeMax() );

        session.setStatLatitudeMin( sessionForm.getStatLatitudeMin() );

        session.setStatLongitudeMax( sessionForm.getStatLongitudeMax() );

        session.setStatLongitudeMin( sessionForm.getStatLongitudeMin() );

        session.setStatPedometerCadenceAverage( sessionForm.getStatPedometerCadenceAverage() );

        session.setStatPedometerCadenceMax( sessionForm.getStatPedometerCadenceMax() );

        session.setStatPedometerCadenceMedian( sessionForm.getStatPedometerCadenceMedian() );

        session.setStatPedometerGroundContactTimeAverage( sessionForm.getStatPedometerGroundContactTimeAverage() );

        session.setStatPedometerGroundContactTimeMax( sessionForm.getStatPedometerGroundContactTimeMax() );

        session.setStatPedometerGroundContactTimeMedian( sessionForm.getStatPedometerGroundContactTimeMedian() );

        session.setStatPedometerStride( sessionForm.getStatPedometerStride() );

        session.setStatPedometerTotal( sessionForm.getStatPedometerTotal() );

        session.setStatPedometerVerticalOscillationAverage( sessionForm.getStatPedometerVerticalOscillationAverage() );

        session.setStatPedometerVerticalOscillationMax( sessionForm.getStatPedometerVerticalOscillationMax() );

        session.setStatPedometerVerticalOscillationMedian( sessionForm.getStatPedometerVerticalOscillationMedian() );

        session.setStatPowerAverage( sessionForm.getStatPowerAverage() );

        session.setStatPowerMax( sessionForm.getStatPowerMax() );

        session.setStatPowerMedian( sessionForm.getStatPowerMedian() );

        session.setStatSpeedAverage( sessionForm.getStatSpeedAverage() );

        session.setStatSpeedAverageMoving( sessionForm.getStatSpeedAverageMoving() );

        session.setStatSpeedMax( sessionForm.getStatSpeedMax() );

        session.setStatTimeMoving( sessionForm.getStatTimeMoving() );

        session.setStatTimeTotal( sessionForm.getStatTimeTotal() );

        session.setStatWheelCadenceAverage( sessionForm.getStatWheelCadenceAverage() );

        session.setStatWheelCadenceMax( sessionForm.getStatWheelCadenceMax() );

        session.setStatWheelCadenceMedian( sessionForm.getStatWheelCadenceMedian() );

        session.setStatWheelPerimeter( sessionForm.getStatWheelPerimeter() );

        session.setStatWheelRevolutionTotal( sessionForm.getStatWheelRevolutionTotal() );

        session.setTargetCadence( sessionForm.getTargetCadence() );

        session.setTargetCadenceFeedbackMode( sessionForm.getTargetCadenceFeedbackMode() );

        session.setTargetCadenceFeedbackType( sessionForm.getTargetCadenceFeedbackType() );

        session.setTargetDistance( sessionForm.getTargetDistance() );

        session.setTargetDistanceFeedbackMode( sessionForm.getTargetDistanceFeedbackMode() );

        session.setTargetDistanceFeedbackType( sessionForm.getTargetDistanceFeedbackType() );

        session.setTargetHeartRate( sessionForm.getTargetHeartRate() );

        session.setTargetHeartRateFeedbackMode( sessionForm.getTargetHeartRateFeedbackMode() );

        session.setTargetHeartRateFeedbackType( sessionForm.getTargetHeartRateFeedbackType() );

        session.setTargetPower( sessionForm.getTargetPower() );

        session.setTargetPowerFeedbackMode( sessionForm.getTargetPowerFeedbackMode() );

        session.setTargetPowerFeedbackType( sessionForm.getTargetPowerFeedbackType() );

        session.setTargetSpeedPace( sessionForm.getTargetSpeedPace() );

        session.setTargetSpeedPaceFeedbackMode( sessionForm.getTargetSpeedPaceFeedbackMode() );

        session.setTargetSpeedPaceFeedbackType( sessionForm.getTargetSpeedPaceFeedbackType() );

        session.setTargetTime( sessionForm.getTargetTime() );

        session.setTargetTimeFeedbackMode( sessionForm.getTargetTimeFeedbackMode() );

        session.setTargetTimeFeedbackType( sessionForm.getTargetTimeFeedbackType() );
    }

    @Override

    public TrackPeriodForm toTrackPeriodForm(TrackPeriod trackPeriod) {

        if ( trackPeriod == null ) {

            return null;
        }

        TrackPeriodForm trackPeriodForm = new TrackPeriodForm();

        trackPeriodForm.setBusinessVersion( trackPeriod.getBusinessVersion() );

        trackPeriodForm.setDateEnd( trackPeriod.getDateEnd() );

        trackPeriodForm.setDateStart( trackPeriod.getDateStart() );

        trackPeriodForm.setId( trackPeriod.getId() );

        trackPeriodForm.setSleepOverrideEnd( trackPeriod.getSleepOverrideEnd() );

        trackPeriodForm.setSleepOverrideStart( trackPeriod.getSleepOverrideStart() );

        return trackPeriodForm;
    }

    @Override

    public TrackPeriod toTrackPeriod(TrackPeriodForm trackPeriodForm) {

        if ( trackPeriodForm == null ) {

            return null;
        }

        TrackPeriod trackPeriod = new TrackPeriod();

        trackPeriod.setDateEnd( trackPeriodForm.getDateEnd() );

        trackPeriod.setDateStart( trackPeriodForm.getDateStart() );

        trackPeriod.setBusinessVersion( trackPeriodForm.getBusinessVersion() );

        trackPeriod.setSleepOverrideEnd( trackPeriodForm.getSleepOverrideEnd() );

        trackPeriod.setSleepOverrideStart( trackPeriodForm.getSleepOverrideStart() );

        return trackPeriod;
    }

    @Override

    public PageForm<TrackPeriodForm> toTrackPeriodForms(Page<TrackPeriod> trackPeriod) {

        if ( trackPeriod == null ) {

            return null;
        }

        PageForm<TrackPeriodForm> pageForm = new PageForm<TrackPeriodForm> ();

        for ( TrackPeriod trackPeriod_ : trackPeriod ) {

            pageForm.add( toTrackPeriodForm( trackPeriod_ ) );
        }

        formMapperUtil.onAfterPage( trackPeriod, pageForm );

        return pageForm;
    }

    @Override

    public List<TrackPeriodForm> toTrackPeriodForms(List<TrackPeriod> trackPeriod) {

        if ( trackPeriod == null ) {

            return null;
        }

        List<TrackPeriodForm> list = new ArrayList<TrackPeriodForm>();

        for ( TrackPeriod trackPeriod_ : trackPeriod ) {

            list.add( toTrackPeriodForm( trackPeriod_ ) );
        }

        return list;
    }

    @Override

    public void update(TrackPeriodForm trackPeriodForm, TrackPeriod trackPeriod) {

        if ( trackPeriodForm == null ) {

            return;
        }

        trackPeriod.setDateEnd( trackPeriodForm.getDateEnd() );

        trackPeriod.setDateStart( trackPeriodForm.getDateStart() );

        trackPeriod.setBusinessVersion( trackPeriodForm.getBusinessVersion() );

        trackPeriod.setSleepOverrideEnd( trackPeriodForm.getSleepOverrideEnd() );

        trackPeriod.setSleepOverrideStart( trackPeriodForm.getSleepOverrideStart() );
    }

    @Override

    public SessionExtendedForm toSessionExtendedForm(Session session) {

        if ( session == null ) {

            return null;
        }

        SessionExtendedForm sessionExtendedForm = new SessionExtendedForm();

        sessionExtendedForm.setBusinessVersion( session.getBusinessVersion() );

        sessionExtendedForm.setDataProviderHash( session.getDataProviderHash() );

        sessionExtendedForm.setDataProviderUrl( session.getDataProviderUrl() );

        sessionExtendedForm.setDataThumbnailUrl( session.getDataThumbnailUrl() );

        sessionExtendedForm.setDateEnd( session.getDateEnd() );

        sessionExtendedForm.setDateStart( session.getDateStart() );

        sessionExtendedForm.setDescription( session.getDescription() );

        sessionExtendedForm.setEquipment( formMapperUtil.map( session.getEquipment() ) );

        sessionExtendedForm.setId( session.getId() );

        sessionExtendedForm.setName( session.getName() );

        sessionExtendedForm.setSessionType( session.getSessionType() );

        sessionExtendedForm.setStatAltitudeCumulativeGain( session.getStatAltitudeCumulativeGain() );

        sessionExtendedForm.setStatAltitudeCumulativeLoss( session.getStatAltitudeCumulativeLoss() );

        sessionExtendedForm.setStatAltitudeMax( session.getStatAltitudeMax() );

        sessionExtendedForm.setStatAltitudeMin( session.getStatAltitudeMin() );

        sessionExtendedForm.setStatCalculation( session.getStatCalculation() );

        sessionExtendedForm.setStatCalories( session.getStatCalories() );

        sessionExtendedForm.setStatCrankCadenceAverage( session.getStatCrankCadenceAverage() );

        sessionExtendedForm.setStatCrankCadenceMax( session.getStatCrankCadenceMax() );

        sessionExtendedForm.setStatCrankCadenceMedian( session.getStatCrankCadenceMedian() );

        sessionExtendedForm.setStatCrankRevolutionTotal( session.getStatCrankRevolutionTotal() );

        sessionExtendedForm.setStatDistance( session.getStatDistance() );

        sessionExtendedForm.setStatDistanceLocation( session.getStatDistanceLocation() );

        sessionExtendedForm.setStatGradeMax( session.getStatGradeMax() );

        sessionExtendedForm.setStatGradeMin( session.getStatGradeMin() );

        sessionExtendedForm.setStatHeartRateAverage( session.getStatHeartRateAverage() );

        sessionExtendedForm.setStatHeartRateHardMillis( session.getStatHeartRateHardMillis() );

        sessionExtendedForm.setStatHeartRateLightMillis( session.getStatHeartRateLightMillis() );

        sessionExtendedForm.setStatHeartRateMax( session.getStatHeartRateMax() );

        sessionExtendedForm.setStatHeartRateMaxMillis( session.getStatHeartRateMaxMillis() );

        sessionExtendedForm.setStatHeartRateMedian( session.getStatHeartRateMedian() );

        sessionExtendedForm.setStatHeartRateMin( session.getStatHeartRateMin() );

        sessionExtendedForm.setStatHeartRateModerateMillis( session.getStatHeartRateModerateMillis() );

        sessionExtendedForm.setStatHeartRateRestMillis( session.getStatHeartRateRestMillis() );

        sessionExtendedForm.setStatHeartRateVeryLightMillis( session.getStatHeartRateVeryLightMillis() );

        sessionExtendedForm.setStatLatitudeMax( session.getStatLatitudeMax() );

        sessionExtendedForm.setStatLatitudeMin( session.getStatLatitudeMin() );

        sessionExtendedForm.setStatLongitudeMax( session.getStatLongitudeMax() );

        sessionExtendedForm.setStatLongitudeMin( session.getStatLongitudeMin() );

        sessionExtendedForm.setStatPedometerCadenceAverage( session.getStatPedometerCadenceAverage() );

        sessionExtendedForm.setStatPedometerCadenceMax( session.getStatPedometerCadenceMax() );

        sessionExtendedForm.setStatPedometerCadenceMedian( session.getStatPedometerCadenceMedian() );

        sessionExtendedForm.setStatPedometerGroundContactTimeAverage( session.getStatPedometerGroundContactTimeAverage() );

        sessionExtendedForm.setStatPedometerGroundContactTimeMax( session.getStatPedometerGroundContactTimeMax() );

        sessionExtendedForm.setStatPedometerGroundContactTimeMedian( session.getStatPedometerGroundContactTimeMedian() );

        sessionExtendedForm.setStatPedometerStride( session.getStatPedometerStride() );

        sessionExtendedForm.setStatPedometerTotal( session.getStatPedometerTotal() );

        sessionExtendedForm.setStatPedometerVerticalOscillationAverage( session.getStatPedometerVerticalOscillationAverage() );

        sessionExtendedForm.setStatPedometerVerticalOscillationMax( session.getStatPedometerVerticalOscillationMax() );

        sessionExtendedForm.setStatPedometerVerticalOscillationMedian( session.getStatPedometerVerticalOscillationMedian() );

        sessionExtendedForm.setStatPowerAverage( session.getStatPowerAverage() );

        sessionExtendedForm.setStatPowerMax( session.getStatPowerMax() );

        sessionExtendedForm.setStatPowerMedian( session.getStatPowerMedian() );

        sessionExtendedForm.setStatSpeedAverage( session.getStatSpeedAverage() );

        sessionExtendedForm.setStatSpeedAverageMoving( session.getStatSpeedAverageMoving() );

        sessionExtendedForm.setStatSpeedMax( session.getStatSpeedMax() );

        sessionExtendedForm.setStatTimeMoving( session.getStatTimeMoving() );

        sessionExtendedForm.setStatTimeTotal( session.getStatTimeTotal() );

        sessionExtendedForm.setStatWheelCadenceAverage( session.getStatWheelCadenceAverage() );

        sessionExtendedForm.setStatWheelCadenceMax( session.getStatWheelCadenceMax() );

        sessionExtendedForm.setStatWheelCadenceMedian( session.getStatWheelCadenceMedian() );

        sessionExtendedForm.setStatWheelPerimeter( session.getStatWheelPerimeter() );

        sessionExtendedForm.setStatWheelRevolutionTotal( session.getStatWheelRevolutionTotal() );

        sessionExtendedForm.setTargetCadence( session.getTargetCadence() );

        sessionExtendedForm.setTargetCadenceFeedbackMode( session.getTargetCadenceFeedbackMode() );

        sessionExtendedForm.setTargetCadenceFeedbackType( session.getTargetCadenceFeedbackType() );

        sessionExtendedForm.setTargetDistance( session.getTargetDistance() );

        sessionExtendedForm.setTargetDistanceFeedbackMode( session.getTargetDistanceFeedbackMode() );

        sessionExtendedForm.setTargetDistanceFeedbackType( session.getTargetDistanceFeedbackType() );

        sessionExtendedForm.setTargetHeartRate( session.getTargetHeartRate() );

        sessionExtendedForm.setTargetHeartRateFeedbackMode( session.getTargetHeartRateFeedbackMode() );

        sessionExtendedForm.setTargetHeartRateFeedbackType( session.getTargetHeartRateFeedbackType() );

        sessionExtendedForm.setTargetPower( session.getTargetPower() );

        sessionExtendedForm.setTargetPowerFeedbackMode( session.getTargetPowerFeedbackMode() );

        sessionExtendedForm.setTargetPowerFeedbackType( session.getTargetPowerFeedbackType() );

        sessionExtendedForm.setTargetSpeedPace( session.getTargetSpeedPace() );

        sessionExtendedForm.setTargetSpeedPaceFeedbackMode( session.getTargetSpeedPaceFeedbackMode() );

        sessionExtendedForm.setTargetSpeedPaceFeedbackType( session.getTargetSpeedPaceFeedbackType() );

        sessionExtendedForm.setTargetTime( session.getTargetTime() );

        sessionExtendedForm.setTargetTimeFeedbackMode( session.getTargetTimeFeedbackMode() );

        sessionExtendedForm.setTargetTimeFeedbackType( session.getTargetTimeFeedbackType() );

        sessionExtendedForm.setTrackPeriods( toTrackPeriodExtendedForms( session.getTrackPeriods() ) );

        return sessionExtendedForm;
    }

    @Override

    public Session toSession(SessionExtendedForm sessionForm, UserProfile userProfile) {

        if ( sessionForm == null && userProfile == null ) {

            return null;
        }

        Session session = new Session();

        if ( sessionForm != null ) {

            session.setTrackPeriods( trackPeriodExtendedFormListToTrackPeriodList( sessionForm.getTrackPeriods() ) );

            session.setBusinessVersion( sessionForm.getBusinessVersion() );

            session.setDataProviderHash( sessionForm.getDataProviderHash() );

            session.setDataProviderUrl( sessionForm.getDataProviderUrl() );

            session.setDataThumbnailUrl( sessionForm.getDataThumbnailUrl() );

            session.setDateEnd( sessionForm.getDateEnd() );

            session.setDateStart( sessionForm.getDateStart() );

            session.setDescription( sessionForm.getDescription() );

            session.setName( sessionForm.getName() );

            session.setSessionType( sessionForm.getSessionType() );

            session.setStatAltitudeCumulativeGain( sessionForm.getStatAltitudeCumulativeGain() );

            session.setStatAltitudeCumulativeLoss( sessionForm.getStatAltitudeCumulativeLoss() );

            session.setStatAltitudeMax( sessionForm.getStatAltitudeMax() );

            session.setStatAltitudeMin( sessionForm.getStatAltitudeMin() );

            session.setStatCalculation( sessionForm.getStatCalculation() );

            session.setStatCalories( sessionForm.getStatCalories() );

            session.setStatCrankCadenceAverage( sessionForm.getStatCrankCadenceAverage() );

            session.setStatCrankCadenceMax( sessionForm.getStatCrankCadenceMax() );

            session.setStatCrankCadenceMedian( sessionForm.getStatCrankCadenceMedian() );

            session.setStatCrankRevolutionTotal( sessionForm.getStatCrankRevolutionTotal() );

            session.setStatDistance( sessionForm.getStatDistance() );

            session.setStatDistanceLocation( sessionForm.getStatDistanceLocation() );

            session.setStatGradeMax( sessionForm.getStatGradeMax() );

            session.setStatGradeMin( sessionForm.getStatGradeMin() );

            session.setStatHeartRateAverage( sessionForm.getStatHeartRateAverage() );

            session.setStatHeartRateHardMillis( sessionForm.getStatHeartRateHardMillis() );

            session.setStatHeartRateLightMillis( sessionForm.getStatHeartRateLightMillis() );

            session.setStatHeartRateMax( sessionForm.getStatHeartRateMax() );

            session.setStatHeartRateMaxMillis( sessionForm.getStatHeartRateMaxMillis() );

            session.setStatHeartRateMedian( sessionForm.getStatHeartRateMedian() );

            session.setStatHeartRateMin( sessionForm.getStatHeartRateMin() );

            session.setStatHeartRateModerateMillis( sessionForm.getStatHeartRateModerateMillis() );

            session.setStatHeartRateRestMillis( sessionForm.getStatHeartRateRestMillis() );

            session.setStatHeartRateVeryLightMillis( sessionForm.getStatHeartRateVeryLightMillis() );

            session.setStatLatitudeMax( sessionForm.getStatLatitudeMax() );

            session.setStatLatitudeMin( sessionForm.getStatLatitudeMin() );

            session.setStatLongitudeMax( sessionForm.getStatLongitudeMax() );

            session.setStatLongitudeMin( sessionForm.getStatLongitudeMin() );

            session.setStatPedometerCadenceAverage( sessionForm.getStatPedometerCadenceAverage() );

            session.setStatPedometerCadenceMax( sessionForm.getStatPedometerCadenceMax() );

            session.setStatPedometerCadenceMedian( sessionForm.getStatPedometerCadenceMedian() );

            session.setStatPedometerGroundContactTimeAverage( sessionForm.getStatPedometerGroundContactTimeAverage() );

            session.setStatPedometerGroundContactTimeMax( sessionForm.getStatPedometerGroundContactTimeMax() );

            session.setStatPedometerGroundContactTimeMedian( sessionForm.getStatPedometerGroundContactTimeMedian() );

            session.setStatPedometerStride( sessionForm.getStatPedometerStride() );

            session.setStatPedometerTotal( sessionForm.getStatPedometerTotal() );

            session.setStatPedometerVerticalOscillationAverage( sessionForm.getStatPedometerVerticalOscillationAverage() );

            session.setStatPedometerVerticalOscillationMax( sessionForm.getStatPedometerVerticalOscillationMax() );

            session.setStatPedometerVerticalOscillationMedian( sessionForm.getStatPedometerVerticalOscillationMedian() );

            session.setStatPowerAverage( sessionForm.getStatPowerAverage() );

            session.setStatPowerMax( sessionForm.getStatPowerMax() );

            session.setStatPowerMedian( sessionForm.getStatPowerMedian() );

            session.setStatSpeedAverage( sessionForm.getStatSpeedAverage() );

            session.setStatSpeedAverageMoving( sessionForm.getStatSpeedAverageMoving() );

            session.setStatSpeedMax( sessionForm.getStatSpeedMax() );

            session.setStatTimeMoving( sessionForm.getStatTimeMoving() );

            session.setStatTimeTotal( sessionForm.getStatTimeTotal() );

            session.setStatWheelCadenceAverage( sessionForm.getStatWheelCadenceAverage() );

            session.setStatWheelCadenceMax( sessionForm.getStatWheelCadenceMax() );

            session.setStatWheelCadenceMedian( sessionForm.getStatWheelCadenceMedian() );

            session.setStatWheelPerimeter( sessionForm.getStatWheelPerimeter() );

            session.setStatWheelRevolutionTotal( sessionForm.getStatWheelRevolutionTotal() );

            session.setTargetCadence( sessionForm.getTargetCadence() );

            session.setTargetCadenceFeedbackMode( sessionForm.getTargetCadenceFeedbackMode() );

            session.setTargetCadenceFeedbackType( sessionForm.getTargetCadenceFeedbackType() );

            session.setTargetDistance( sessionForm.getTargetDistance() );

            session.setTargetDistanceFeedbackMode( sessionForm.getTargetDistanceFeedbackMode() );

            session.setTargetDistanceFeedbackType( sessionForm.getTargetDistanceFeedbackType() );

            session.setTargetHeartRate( sessionForm.getTargetHeartRate() );

            session.setTargetHeartRateFeedbackMode( sessionForm.getTargetHeartRateFeedbackMode() );

            session.setTargetHeartRateFeedbackType( sessionForm.getTargetHeartRateFeedbackType() );

            session.setTargetPower( sessionForm.getTargetPower() );

            session.setTargetPowerFeedbackMode( sessionForm.getTargetPowerFeedbackMode() );

            session.setTargetPowerFeedbackType( sessionForm.getTargetPowerFeedbackType() );

            session.setTargetSpeedPace( sessionForm.getTargetSpeedPace() );

            session.setTargetSpeedPaceFeedbackMode( sessionForm.getTargetSpeedPaceFeedbackMode() );

            session.setTargetSpeedPaceFeedbackType( sessionForm.getTargetSpeedPaceFeedbackType() );

            session.setTargetTime( sessionForm.getTargetTime() );

            session.setTargetTimeFeedbackMode( sessionForm.getTargetTimeFeedbackMode() );

            session.setTargetTimeFeedbackType( sessionForm.getTargetTimeFeedbackType() );
        }

        if ( userProfile != null ) {

            session.setUserProfile( userProfile );
        }

        return session;
    }

    @Override

    public void update(SessionExtendedForm sessionForm, Session session) {

        if ( sessionForm == null ) {

            return;
        }

        List<TrackPeriod> list = trackPeriodExtendedFormListToTrackPeriodList( sessionForm.getTrackPeriods() );

        if ( session.getTrackPeriods() != null ) {

            session.getTrackPeriods().clear();

            if ( list != null ) {

                session.getTrackPeriods().addAll( list );
            }
        }

        else {

            session.setTrackPeriods( list );
        }

        session.setBusinessVersion( sessionForm.getBusinessVersion() );

        session.setDataProviderHash( sessionForm.getDataProviderHash() );

        session.setDataProviderUrl( sessionForm.getDataProviderUrl() );

        session.setDataThumbnailUrl( sessionForm.getDataThumbnailUrl() );

        session.setDateEnd( sessionForm.getDateEnd() );

        session.setDateStart( sessionForm.getDateStart() );

        session.setDescription( sessionForm.getDescription() );

        session.setEquipment( formMapperUtil.map( sessionForm.getEquipment() ) );

        session.setName( sessionForm.getName() );

        session.setSessionType( sessionForm.getSessionType() );

        session.setStatAltitudeCumulativeGain( sessionForm.getStatAltitudeCumulativeGain() );

        session.setStatAltitudeCumulativeLoss( sessionForm.getStatAltitudeCumulativeLoss() );

        session.setStatAltitudeMax( sessionForm.getStatAltitudeMax() );

        session.setStatAltitudeMin( sessionForm.getStatAltitudeMin() );

        session.setStatCalculation( sessionForm.getStatCalculation() );

        session.setStatCalories( sessionForm.getStatCalories() );

        session.setStatCrankCadenceAverage( sessionForm.getStatCrankCadenceAverage() );

        session.setStatCrankCadenceMax( sessionForm.getStatCrankCadenceMax() );

        session.setStatCrankCadenceMedian( sessionForm.getStatCrankCadenceMedian() );

        session.setStatCrankRevolutionTotal( sessionForm.getStatCrankRevolutionTotal() );

        session.setStatDistance( sessionForm.getStatDistance() );

        session.setStatDistanceLocation( sessionForm.getStatDistanceLocation() );

        session.setStatGradeMax( sessionForm.getStatGradeMax() );

        session.setStatGradeMin( sessionForm.getStatGradeMin() );

        session.setStatHeartRateAverage( sessionForm.getStatHeartRateAverage() );

        session.setStatHeartRateHardMillis( sessionForm.getStatHeartRateHardMillis() );

        session.setStatHeartRateLightMillis( sessionForm.getStatHeartRateLightMillis() );

        session.setStatHeartRateMax( sessionForm.getStatHeartRateMax() );

        session.setStatHeartRateMaxMillis( sessionForm.getStatHeartRateMaxMillis() );

        session.setStatHeartRateMedian( sessionForm.getStatHeartRateMedian() );

        session.setStatHeartRateMin( sessionForm.getStatHeartRateMin() );

        session.setStatHeartRateModerateMillis( sessionForm.getStatHeartRateModerateMillis() );

        session.setStatHeartRateRestMillis( sessionForm.getStatHeartRateRestMillis() );

        session.setStatHeartRateVeryLightMillis( sessionForm.getStatHeartRateVeryLightMillis() );

        session.setStatLatitudeMax( sessionForm.getStatLatitudeMax() );

        session.setStatLatitudeMin( sessionForm.getStatLatitudeMin() );

        session.setStatLongitudeMax( sessionForm.getStatLongitudeMax() );

        session.setStatLongitudeMin( sessionForm.getStatLongitudeMin() );

        session.setStatPedometerCadenceAverage( sessionForm.getStatPedometerCadenceAverage() );

        session.setStatPedometerCadenceMax( sessionForm.getStatPedometerCadenceMax() );

        session.setStatPedometerCadenceMedian( sessionForm.getStatPedometerCadenceMedian() );

        session.setStatPedometerGroundContactTimeAverage( sessionForm.getStatPedometerGroundContactTimeAverage() );

        session.setStatPedometerGroundContactTimeMax( sessionForm.getStatPedometerGroundContactTimeMax() );

        session.setStatPedometerGroundContactTimeMedian( sessionForm.getStatPedometerGroundContactTimeMedian() );

        session.setStatPedometerStride( sessionForm.getStatPedometerStride() );

        session.setStatPedometerTotal( sessionForm.getStatPedometerTotal() );

        session.setStatPedometerVerticalOscillationAverage( sessionForm.getStatPedometerVerticalOscillationAverage() );

        session.setStatPedometerVerticalOscillationMax( sessionForm.getStatPedometerVerticalOscillationMax() );

        session.setStatPedometerVerticalOscillationMedian( sessionForm.getStatPedometerVerticalOscillationMedian() );

        session.setStatPowerAverage( sessionForm.getStatPowerAverage() );

        session.setStatPowerMax( sessionForm.getStatPowerMax() );

        session.setStatPowerMedian( sessionForm.getStatPowerMedian() );

        session.setStatSpeedAverage( sessionForm.getStatSpeedAverage() );

        session.setStatSpeedAverageMoving( sessionForm.getStatSpeedAverageMoving() );

        session.setStatSpeedMax( sessionForm.getStatSpeedMax() );

        session.setStatTimeMoving( sessionForm.getStatTimeMoving() );

        session.setStatTimeTotal( sessionForm.getStatTimeTotal() );

        session.setStatWheelCadenceAverage( sessionForm.getStatWheelCadenceAverage() );

        session.setStatWheelCadenceMax( sessionForm.getStatWheelCadenceMax() );

        session.setStatWheelCadenceMedian( sessionForm.getStatWheelCadenceMedian() );

        session.setStatWheelPerimeter( sessionForm.getStatWheelPerimeter() );

        session.setStatWheelRevolutionTotal( sessionForm.getStatWheelRevolutionTotal() );

        session.setTargetCadence( sessionForm.getTargetCadence() );

        session.setTargetCadenceFeedbackMode( sessionForm.getTargetCadenceFeedbackMode() );

        session.setTargetCadenceFeedbackType( sessionForm.getTargetCadenceFeedbackType() );

        session.setTargetDistance( sessionForm.getTargetDistance() );

        session.setTargetDistanceFeedbackMode( sessionForm.getTargetDistanceFeedbackMode() );

        session.setTargetDistanceFeedbackType( sessionForm.getTargetDistanceFeedbackType() );

        session.setTargetHeartRate( sessionForm.getTargetHeartRate() );

        session.setTargetHeartRateFeedbackMode( sessionForm.getTargetHeartRateFeedbackMode() );

        session.setTargetHeartRateFeedbackType( sessionForm.getTargetHeartRateFeedbackType() );

        session.setTargetPower( sessionForm.getTargetPower() );

        session.setTargetPowerFeedbackMode( sessionForm.getTargetPowerFeedbackMode() );

        session.setTargetPowerFeedbackType( sessionForm.getTargetPowerFeedbackType() );

        session.setTargetSpeedPace( sessionForm.getTargetSpeedPace() );

        session.setTargetSpeedPaceFeedbackMode( sessionForm.getTargetSpeedPaceFeedbackMode() );

        session.setTargetSpeedPaceFeedbackType( sessionForm.getTargetSpeedPaceFeedbackType() );

        session.setTargetTime( sessionForm.getTargetTime() );

        session.setTargetTimeFeedbackMode( sessionForm.getTargetTimeFeedbackMode() );

        session.setTargetTimeFeedbackType( sessionForm.getTargetTimeFeedbackType() );
    }

    @Override

    public TrackPeriodExtendedForm toTrackPeriodExtendedForm(TrackPeriod trackPeriod) {

        if ( trackPeriod == null ) {

            return null;
        }

        TrackPeriodExtendedForm trackPeriodExtendedForm = new TrackPeriodExtendedForm();

        trackPeriodExtendedForm.setBusinessVersion( trackPeriod.getBusinessVersion() );

        trackPeriodExtendedForm.setDateEnd( trackPeriod.getDateEnd() );

        trackPeriodExtendedForm.setDateStart( trackPeriod.getDateStart() );

        trackPeriodExtendedForm.setId( trackPeriod.getId() );

        trackPeriodExtendedForm.setSleepOverrideEnd( trackPeriod.getSleepOverrideEnd() );

        trackPeriodExtendedForm.setSleepOverrideStart( trackPeriod.getSleepOverrideStart() );

        trackPeriodExtendedForm.setTrackPoints( toTrackPointForms( trackPeriod.getTrackPoints() ) );

        return trackPeriodExtendedForm;
    }

    @Override

    public PageForm<TrackPeriodExtendedForm> toTrackPeriodExtendedForms(Page<TrackPeriod> trackPeriod) {

        if ( trackPeriod == null ) {

            return null;
        }

        PageForm<TrackPeriodExtendedForm> pageForm = new PageForm<TrackPeriodExtendedForm> ();

        for ( TrackPeriod trackPeriod_ : trackPeriod ) {

            pageForm.add( toTrackPeriodExtendedForm( trackPeriod_ ) );
        }

        formMapperUtil.onAfterPage( trackPeriod, pageForm );

        return pageForm;
    }

    @Override

    public List<TrackPeriodExtendedForm> toTrackPeriodExtendedForms(List<TrackPeriod> trackPeriod) {

        if ( trackPeriod == null ) {

            return null;
        }

        List<TrackPeriodExtendedForm> list_ = new ArrayList<TrackPeriodExtendedForm>();

        for ( TrackPeriod trackPeriod_ : trackPeriod ) {

            list_.add( toTrackPeriodExtendedForm( trackPeriod_ ) );
        }

        return list_;
    }

    @Override

    public TrackPeriod toTrackPeriod(TrackPeriodExtendedForm trackPeriodForm) {

        if ( trackPeriodForm == null ) {

            return null;
        }

        TrackPeriod trackPeriod__ = new TrackPeriod();

        trackPeriod__.setDateEnd( trackPeriodForm.getDateEnd() );

        trackPeriod__.setDateStart( trackPeriodForm.getDateStart() );

        trackPeriod__.setBusinessVersion( trackPeriodForm.getBusinessVersion() );

        trackPeriod__.setSleepOverrideEnd( trackPeriodForm.getSleepOverrideEnd() );

        trackPeriod__.setSleepOverrideStart( trackPeriodForm.getSleepOverrideStart() );

        trackPeriod__.setTrackPoints( trackPointFormListToTrackPointList( trackPeriodForm.getTrackPoints() ) );

        return trackPeriod__;
    }

    @Override

    public void update(TrackPeriodExtendedForm trackPeriodForm, TrackPeriod trackPeriod) {

        if ( trackPeriodForm == null ) {

            return;
        }

        trackPeriod.setDateEnd( trackPeriodForm.getDateEnd() );

        trackPeriod.setDateStart( trackPeriodForm.getDateStart() );

        trackPeriod.setBusinessVersion( trackPeriodForm.getBusinessVersion() );

        trackPeriod.setSleepOverrideEnd( trackPeriodForm.getSleepOverrideEnd() );

        trackPeriod.setSleepOverrideStart( trackPeriodForm.getSleepOverrideStart() );

        List<TrackPoint> list = trackPointFormListToTrackPointList( trackPeriodForm.getTrackPoints() );

        if ( trackPeriod.getTrackPoints() != null ) {

            trackPeriod.getTrackPoints().clear();

            if ( list != null ) {

                trackPeriod.getTrackPoints().addAll( list );
            }
        }

        else {

            trackPeriod.setTrackPoints( list );
        }
    }

    @Override

    public void updateWithoutPoints(TrackPeriodExtendedForm trackPeriodForm, TrackPeriod trackPeriod) {

        if ( trackPeriodForm == null ) {

            return;
        }

        trackPeriod.setDateEnd( trackPeriodForm.getDateEnd() );

        trackPeriod.setDateStart( trackPeriodForm.getDateStart() );

        trackPeriod.setBusinessVersion( trackPeriodForm.getBusinessVersion() );

        trackPeriod.setSleepOverrideEnd( trackPeriodForm.getSleepOverrideEnd() );

        trackPeriod.setSleepOverrideStart( trackPeriodForm.getSleepOverrideStart() );
    }

    @Override

    public TrackPointForm toTrackPointForm(TrackPoint trackPoint) {

        if ( trackPoint == null ) {

            return null;
        }

        TrackPointForm trackPointForm = new TrackPointForm();

        trackPointForm.setActivityIntensity( trackPoint.getActivityIntensity() );

        trackPointForm.setActivityLevel( trackPoint.getActivityLevel() );

        trackPointForm.setAltitude( trackPoint.getAltitude() );

        trackPointForm.setAltitudeDelta( trackPoint.getAltitudeDelta() );

        trackPointForm.setBearing( trackPoint.getBearing() );

        trackPointForm.setBloodPressureDiastolic( trackPoint.getBloodPressureDiastolic() );

        trackPointForm.setBloodPressureSystolic( trackPoint.getBloodPressureSystolic() );

        trackPointForm.setCaloriesDelta( trackPoint.getCaloriesDelta() );

        trackPointForm.setComment( trackPoint.getComment() );

        trackPointForm.setCrankCadence( trackPoint.getCrankCadence() );

        trackPointForm.setCrankRevolutionDelta( trackPoint.getCrankRevolutionDelta() );

        trackPointForm.setDate( trackPoint.getDate() );

        trackPointForm.setDescription( trackPoint.getDescription() );

        trackPointForm.setGlucose( trackPoint.getGlucose() );

        trackPointForm.setHeartRate( trackPoint.getHeartRate() );

        trackPointForm.setId( trackPoint.getId() );

        trackPointForm.setLatitude( trackPoint.getLatitude() );

        trackPointForm.setLightIntensity( trackPoint.getLightIntensity() );

        trackPointForm.setLocationAccuracy( trackPoint.getLocationAccuracy() );

        trackPointForm.setLocationDistanceDelta( trackPoint.getLocationDistanceDelta() );

        trackPointForm.setLocationSpeed( trackPoint.getLocationSpeed() );

        trackPointForm.setLocationTime( trackPoint.getLocationTime() );

        trackPointForm.setLongitude( trackPoint.getLongitude() );

        trackPointForm.setMoving( trackPoint.isMoving() );

        trackPointForm.setName( trackPoint.getName() );

        trackPointForm.setPedometerCadence( trackPoint.getPedometerCadence() );

        trackPointForm.setPedometerGroundContactTime( trackPoint.getPedometerGroundContactTime() );

        trackPointForm.setPedometerStepDelta( trackPoint.getPedometerStepDelta() );

        trackPointForm.setPedometerVerticalOscillation( trackPoint.getPedometerVerticalOscillation() );

        trackPointForm.setPower( trackPoint.getPower() );

        trackPointForm.setSleepDetectMove( trackPoint.getSleepDetectMove() );

        trackPointForm.setSleepDetectSnore( trackPoint.getSleepDetectSnore() );

        trackPointForm.setSleepDetectSuccess( trackPoint.getSleepDetectSuccess() );

        trackPointForm.setTemperature( trackPoint.getTemperature() );

        trackPointForm.setTilt( trackPoint.getTilt() );

        trackPointForm.setTimeDelta( trackPoint.getTimeDelta() );

        trackPointForm.setWheelCadence( trackPoint.getWheelCadence() );

        trackPointForm.setWheelRevolutionDelta( trackPoint.getWheelRevolutionDelta() );

        return trackPointForm;
    }

    @Override

    public TrackPoint toTrackPoint(TrackPointForm trackPointForm) {

        if ( trackPointForm == null ) {

            return null;
        }

        TrackPoint trackPoint__ = new TrackPoint();

        trackPoint__.setActivityIntensity( trackPointForm.getActivityIntensity() );

        trackPoint__.setActivityLevel( trackPointForm.getActivityLevel() );

        trackPoint__.setAltitude( trackPointForm.getAltitude() );

        trackPoint__.setAltitudeDelta( trackPointForm.getAltitudeDelta() );

        trackPoint__.setBearing( trackPointForm.getBearing() );

        trackPoint__.setBloodPressureDiastolic( trackPointForm.getBloodPressureDiastolic() );

        trackPoint__.setBloodPressureSystolic( trackPointForm.getBloodPressureSystolic() );

        trackPoint__.setCaloriesDelta( trackPointForm.getCaloriesDelta() );

        trackPoint__.setComment( trackPointForm.getComment() );

        trackPoint__.setCrankCadence( trackPointForm.getCrankCadence() );

        trackPoint__.setCrankRevolutionDelta( trackPointForm.getCrankRevolutionDelta() );

        trackPoint__.setDate( trackPointForm.getDate() );

        trackPoint__.setDescription( trackPointForm.getDescription() );

        trackPoint__.setGlucose( trackPointForm.getGlucose() );

        trackPoint__.setHeartRate( trackPointForm.getHeartRate() );

        trackPoint__.setLatitude( trackPointForm.getLatitude() );

        trackPoint__.setLightIntensity( trackPointForm.getLightIntensity() );

        trackPoint__.setLocationAccuracy( trackPointForm.getLocationAccuracy() );

        trackPoint__.setLocationDistanceDelta( trackPointForm.getLocationDistanceDelta() );

        trackPoint__.setLocationSpeed( trackPointForm.getLocationSpeed() );

        trackPoint__.setLocationTime( trackPointForm.getLocationTime() );

        trackPoint__.setLongitude( trackPointForm.getLongitude() );

        trackPoint__.setMoving( trackPointForm.isMoving() );

        trackPoint__.setName( trackPointForm.getName() );

        trackPoint__.setPedometerCadence( trackPointForm.getPedometerCadence() );

        trackPoint__.setPedometerGroundContactTime( trackPointForm.getPedometerGroundContactTime() );

        trackPoint__.setPedometerStepDelta( trackPointForm.getPedometerStepDelta() );

        trackPoint__.setPedometerVerticalOscillation( trackPointForm.getPedometerVerticalOscillation() );

        trackPoint__.setPower( trackPointForm.getPower() );

        trackPoint__.setSleepDetectMove( trackPointForm.getSleepDetectMove() );

        trackPoint__.setSleepDetectSnore( trackPointForm.getSleepDetectSnore() );

        trackPoint__.setSleepDetectSuccess( trackPointForm.getSleepDetectSuccess() );

        trackPoint__.setTemperature( trackPointForm.getTemperature() );

        trackPoint__.setTilt( trackPointForm.getTilt() );

        trackPoint__.setTimeDelta( trackPointForm.getTimeDelta() );

        trackPoint__.setWheelCadence( trackPointForm.getWheelCadence() );

        trackPoint__.setWheelRevolutionDelta( trackPointForm.getWheelRevolutionDelta() );

        return trackPoint__;
    }

    @Override

    public PageForm<TrackPointForm> toTrackPointForms(Page<TrackPoint> trackPoints) {

        if ( trackPoints == null ) {

            return null;
        }

        PageForm<TrackPointForm> pageForm = new PageForm<TrackPointForm> ();

        for ( TrackPoint trackPoint : trackPoints ) {

            pageForm.add( toTrackPointForm( trackPoint ) );
        }

        formMapperUtil.onAfterPage( trackPoints, pageForm );

        return pageForm;
    }

    @Override

    public List<TrackPointForm> toTrackPointForms(List<TrackPoint> trackPoints) {

        if ( trackPoints == null ) {

            return null;
        }

        List<TrackPointForm> list_ = new ArrayList<TrackPointForm>();

        for ( TrackPoint trackPoint : trackPoints ) {

            list_.add( toTrackPointForm( trackPoint ) );
        }

        return list_;
    }

    @Override

    public void update(TrackPointForm trackPointForm, TrackPoint trackPoint) {

        if ( trackPointForm == null ) {

            return;
        }

        trackPoint.setActivityIntensity( trackPointForm.getActivityIntensity() );

        trackPoint.setActivityLevel( trackPointForm.getActivityLevel() );

        trackPoint.setAltitude( trackPointForm.getAltitude() );

        trackPoint.setAltitudeDelta( trackPointForm.getAltitudeDelta() );

        trackPoint.setBearing( trackPointForm.getBearing() );

        trackPoint.setBloodPressureDiastolic( trackPointForm.getBloodPressureDiastolic() );

        trackPoint.setBloodPressureSystolic( trackPointForm.getBloodPressureSystolic() );

        trackPoint.setCaloriesDelta( trackPointForm.getCaloriesDelta() );

        trackPoint.setComment( trackPointForm.getComment() );

        trackPoint.setCrankCadence( trackPointForm.getCrankCadence() );

        trackPoint.setCrankRevolutionDelta( trackPointForm.getCrankRevolutionDelta() );

        trackPoint.setDate( trackPointForm.getDate() );

        trackPoint.setDescription( trackPointForm.getDescription() );

        trackPoint.setGlucose( trackPointForm.getGlucose() );

        trackPoint.setHeartRate( trackPointForm.getHeartRate() );

        trackPoint.setLatitude( trackPointForm.getLatitude() );

        trackPoint.setLightIntensity( trackPointForm.getLightIntensity() );

        trackPoint.setLocationAccuracy( trackPointForm.getLocationAccuracy() );

        trackPoint.setLocationDistanceDelta( trackPointForm.getLocationDistanceDelta() );

        trackPoint.setLocationSpeed( trackPointForm.getLocationSpeed() );

        trackPoint.setLocationTime( trackPointForm.getLocationTime() );

        trackPoint.setLongitude( trackPointForm.getLongitude() );

        trackPoint.setMoving( trackPointForm.isMoving() );

        trackPoint.setName( trackPointForm.getName() );

        trackPoint.setPedometerCadence( trackPointForm.getPedometerCadence() );

        trackPoint.setPedometerGroundContactTime( trackPointForm.getPedometerGroundContactTime() );

        trackPoint.setPedometerStepDelta( trackPointForm.getPedometerStepDelta() );

        trackPoint.setPedometerVerticalOscillation( trackPointForm.getPedometerVerticalOscillation() );

        trackPoint.setPower( trackPointForm.getPower() );

        trackPoint.setSleepDetectMove( trackPointForm.getSleepDetectMove() );

        trackPoint.setSleepDetectSnore( trackPointForm.getSleepDetectSnore() );

        trackPoint.setSleepDetectSuccess( trackPointForm.getSleepDetectSuccess() );

        trackPoint.setTemperature( trackPointForm.getTemperature() );

        trackPoint.setTilt( trackPointForm.getTilt() );

        trackPoint.setTimeDelta( trackPointForm.getTimeDelta() );

        trackPoint.setWheelCadence( trackPointForm.getWheelCadence() );

        trackPoint.setWheelRevolutionDelta( trackPointForm.getWheelRevolutionDelta() );
    }

    @Override

    public SummaryForm toSummaryForm(Nutrition nutrition) {

        if ( nutrition == null ) {

            return null;
        }

        SummaryForm summaryForm = new SummaryForm();

        summaryForm.setBusinessVersion( nutrition.getBusinessVersion() );

        summaryForm.setId( nutrition.getId() );

        return summaryForm;
    }

    @Override

    public SummaryForm toSummaryForm(Session session) {

        if ( session == null ) {

            return null;
        }

        SummaryForm summaryForm = new SummaryForm();

        summaryForm.setBusinessVersion( session.getBusinessVersion() );

        summaryForm.setId( session.getId() );

        return summaryForm;
    }

    @Override

    public SummaryForm toSummaryForm(BodyMetric bodyMetric) {

        if ( bodyMetric == null ) {

            return null;
        }

        SummaryForm summaryForm = new SummaryForm();

        summaryForm.setBusinessVersion( bodyMetric.getBusinessVersion() );

        summaryForm.setId( bodyMetric.getId() );

        return summaryForm;
    }

    @Override

    public SummaryForm toSummaryForm(Equipment equipment) {

        if ( equipment == null ) {

            return null;
        }

        SummaryForm summaryForm = new SummaryForm();

        summaryForm.setBusinessVersion( equipment.getBusinessVersion() );

        summaryForm.setId( equipment.getId() );

        return summaryForm;
    }

    @Override

    public SummaryForm toSummaryForm(TrackPeriod trackPeriod) {

        if ( trackPeriod == null ) {

            return null;
        }

        SummaryForm summaryForm = new SummaryForm();

        summaryForm.setBusinessVersion( trackPeriod.getBusinessVersion() );

        summaryForm.setId( trackPeriod.getId() );

        return summaryForm;
    }

    @Override

    public PageForm<SummaryForm> toNutritionSummaryForms(Page<Nutrition> nutritionPage) {

        if ( nutritionPage == null ) {

            return null;
        }

        PageForm<SummaryForm> pageForm = new PageForm<SummaryForm> ();

        for ( Nutrition nutrition : nutritionPage ) {

            pageForm.add( toSummaryForm( nutrition ) );
        }

        formMapperUtil.onAfterPage( nutritionPage, pageForm );

        return pageForm;
    }

    @Override

    public PageForm<SummaryForm> toSessionSummaryForms(Page<Session> sessionPage) {

        if ( sessionPage == null ) {

            return null;
        }

        PageForm<SummaryForm> pageForm = new PageForm<SummaryForm> ();

        for ( Session session : sessionPage ) {

            pageForm.add( toSummaryForm( session ) );
        }

        formMapperUtil.onAfterPage( sessionPage, pageForm );

        return pageForm;
    }

    @Override

    public PageForm<SummaryForm> toBodyMetricSummaryForms(Page<BodyMetric> bodyMetricPage) {

        if ( bodyMetricPage == null ) {

            return null;
        }

        PageForm<SummaryForm> pageForm = new PageForm<SummaryForm> ();

        for ( BodyMetric bodyMetric : bodyMetricPage ) {

            pageForm.add( toSummaryForm( bodyMetric ) );
        }

        formMapperUtil.onAfterPage( bodyMetricPage, pageForm );

        return pageForm;
    }

    @Override

    public PageForm<SummaryForm> toEquipmentSummaryForms(Page<Equipment> equipmentPage) {

        if ( equipmentPage == null ) {

            return null;
        }

        PageForm<SummaryForm> pageForm = new PageForm<SummaryForm> ();

        for ( Equipment equipment : equipmentPage ) {

            pageForm.add( toSummaryForm( equipment ) );
        }

        formMapperUtil.onAfterPage( equipmentPage, pageForm );

        return pageForm;
    }

    @Override

    public PageForm<SummaryForm> toTrackPeriodSummaryForms(Page<TrackPeriod> trackPeriodPage) {

        if ( trackPeriodPage == null ) {

            return null;
        }

        PageForm<SummaryForm> pageForm = new PageForm<SummaryForm> ();

        for ( TrackPeriod trackPeriod : trackPeriodPage ) {

            pageForm.add( toSummaryForm( trackPeriod ) );
        }

        formMapperUtil.onAfterPage( trackPeriodPage, pageForm );

        return pageForm;
    }

    protected List<TrackPeriod> trackPeriodExtendedFormListToTrackPeriodList(List<TrackPeriodExtendedForm> list) {

        if ( list == null ) {

            return null;
        }

        List<TrackPeriod> list_ = new ArrayList<TrackPeriod>();

        for ( TrackPeriodExtendedForm trackPeriodExtendedForm : list ) {

            list_.add( toTrackPeriod( trackPeriodExtendedForm ) );
        }

        return list_;
    }

    protected List<TrackPoint> trackPointFormListToTrackPointList(List<TrackPointForm> list) {

        if ( list == null ) {

            return null;
        }

        List<TrackPoint> list_ = new ArrayList<TrackPoint>();

        for ( TrackPointForm trackPointForm : list ) {

            list_.add( toTrackPoint( trackPointForm ) );
        }

        return list_;
    }
}

