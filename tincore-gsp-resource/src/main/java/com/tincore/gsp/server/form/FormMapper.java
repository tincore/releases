package com.tincore.gsp.server.form;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import com.tincore.gsp.server.domain.BodyMetric;
import com.tincore.gsp.server.domain.Equipment;
import com.tincore.gsp.server.domain.Nutrition;
import com.tincore.gsp.server.domain.Session;
import com.tincore.gsp.server.domain.TrackPeriod;
import com.tincore.gsp.server.domain.TrackPoint;
import com.tincore.gsp.server.domain.UserProfile;

@Mapper(componentModel = "spring", uses = FormMapperUtil.class)
public interface FormMapper {

	UserProfileForm toUserProfileForm(UserProfile personalProfile);

	void update(UserProfileForm userProfileForm, @MappingTarget UserProfile userProfile);

	//
	EquipmentForm toEquipmentForm(Equipment equipment);

	@Mapping(source = "equipmentForm.id", target = "id", ignore = true)
	@Mapping(source = "equipmentForm.businessVersion", target = "businessVersion")
	Equipment toEquipment(EquipmentForm equipmentForm, UserProfile userProfile);

	@Mapping(source = "equipmentForm.id", target = "id", ignore = true)
	Equipment toEquipment(EquipmentForm equipmentForm);

	PageForm<EquipmentForm> toEquipmentForms(Page<Equipment> equipment);

	List<EquipmentForm> toEquipmentForms(List<Equipment> equipment);

	@Mapping(source = "id", target = "id", ignore = true)
	void update(EquipmentForm equipmentForm, @MappingTarget Equipment equipment);

	//
	BodyMetricForm toBodyMetricForm(BodyMetric bodyMetric);

	@Mapping(source = "bodyMetricForm.id", target = "id", ignore = true)
	@Mapping(source = "bodyMetricForm.businessVersion", target = "businessVersion")
	BodyMetric toBodyMetric(BodyMetricForm bodyMetricForm, UserProfile userProfile);
	
	PageForm<BodyMetricForm> toBodyMetricForms(Page<BodyMetric> bodyMetric);

	List<BodyMetricForm> toBodyMetricForms(List<BodyMetric> bodyMetric);

	@Mapping(source = "id", target = "id", ignore = true)
	void update(BodyMetricForm bodyMetricForm, @MappingTarget BodyMetric bodyMetric);

	//
	NutritionForm toNutritionForm(Nutrition nutrition);

	@Mapping(source = "nutritionForm.id", target = "id", ignore = true)
	@Mapping(source = "nutritionForm.businessVersion", target = "businessVersion")
	Nutrition toNutrition(NutritionForm nutritionForm, UserProfile userProfile);

	PageForm<NutritionForm> toNutritionForms(Page<Nutrition> nutrition);

	List<NutritionForm> toNutritionForms(List<Nutrition> nutrition);

	@Mapping(source = "id", target = "id", ignore = true)
	void update(NutritionForm nutritionForm, @MappingTarget Nutrition nutrition);

	//
	SessionForm toSessionForm(Session session);

	@Mapping(source = "sessionForm.id", target = "id", ignore = true)
	@Mapping(source = "sessionForm.equipment", target = "equipment", ignore = true)
	@Mapping(source = "sessionForm.businessVersion", target = "businessVersion")
	Session toSession(SessionForm sessionForm, UserProfile userProfile);

	PageForm<SessionForm> toSessionForms(Page<Session> session);

	List<SessionForm> toSessionForms(List<Session> session);

	@Mapping(source = "id", target = "id", ignore = true)
	void update(SessionForm sessionForm, @MappingTarget Session session);

	//
	TrackPeriodForm toTrackPeriodForm(TrackPeriod trackPeriod);

	@Mapping(source = "id", target = "id", ignore = true)
	TrackPeriod toTrackPeriod(TrackPeriodForm trackPeriodForm);

	PageForm<TrackPeriodForm> toTrackPeriodForms(Page<TrackPeriod> trackPeriod);

	List<TrackPeriodForm> toTrackPeriodForms(List<TrackPeriod> trackPeriod);

	@Mapping(source = "id", target = "id", ignore = true)
	void update(TrackPeriodForm trackPeriodForm, @MappingTarget TrackPeriod trackPeriod);

//
	SessionExtendedForm toSessionExtendedForm(Session session);

	@Mapping(source = "sessionForm.id", target = "id", ignore = true)
	@Mapping(source = "sessionForm.equipment", target = "equipment", ignore = true)
	@Mapping(source = "sessionForm.businessVersion", target = "businessVersion")
	Session toSession(SessionExtendedForm sessionForm, UserProfile userProfile);
//
	@Mapping(source = "id", target = "id", ignore = true)
	void update(SessionExtendedForm sessionForm, @MappingTarget Session session);

	//
	TrackPeriodExtendedForm toTrackPeriodExtendedForm(TrackPeriod trackPeriod);
	
	PageForm<TrackPeriodExtendedForm> toTrackPeriodExtendedForms(Page<TrackPeriod> trackPeriod);
	
	List<TrackPeriodExtendedForm> toTrackPeriodExtendedForms(List<TrackPeriod> trackPeriod);

	@Mapping(source = "id", target = "id", ignore = true)
	TrackPeriod toTrackPeriod(TrackPeriodExtendedForm trackPeriodForm);

	@Mapping(source = "id", target = "id", ignore = true)
	void update(TrackPeriodExtendedForm trackPeriodForm, @MappingTarget TrackPeriod trackPeriod);

	@Mapping(source = "id", target = "id", ignore = true)
	@Mapping(source = "trackPeriodForm.trackPoints", target = "trackPoints", ignore = true)
	void updateWithoutPoints(TrackPeriodExtendedForm trackPeriodForm, @MappingTarget TrackPeriod trackPeriod);

	//

	TrackPointForm toTrackPointForm(TrackPoint trackPoint);

	@Mapping(source = "id", target = "id", ignore = true)
	TrackPoint toTrackPoint(TrackPointForm trackPointForm);

	PageForm<TrackPointForm> toTrackPointForms(Page<TrackPoint> trackPoints);

	List<TrackPointForm> toTrackPointForms(List<TrackPoint> trackPoints);

	@Mapping(source = "id", target = "id", ignore = true)
	void update(TrackPointForm trackPointForm, @MappingTarget TrackPoint trackPoint);

	//
	SummaryForm toSummaryForm(Nutrition nutrition);
	SummaryForm toSummaryForm(Session session);
	SummaryForm toSummaryForm(BodyMetric bodyMetric);
	SummaryForm toSummaryForm(Equipment equipment);
	SummaryForm toSummaryForm(TrackPeriod trackPeriod);
	
	PageForm<SummaryForm> toNutritionSummaryForms(Page<Nutrition> nutritionPage);

	PageForm<SummaryForm> toSessionSummaryForms(Page<Session> sessionPage);

	PageForm<SummaryForm> toBodyMetricSummaryForms(Page<BodyMetric> bodyMetricPage);

	PageForm<SummaryForm> toEquipmentSummaryForms(Page<Equipment> equipmentPage);

	PageForm<SummaryForm> toTrackPeriodSummaryForms(Page<TrackPeriod> trackPeriodPage);

}
