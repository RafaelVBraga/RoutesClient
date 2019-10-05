package com.rvbragarouteclient.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Route implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Long id;
	
	@JsonInclude(Include.NON_NULL)
	private String routePlan;
	
	@JsonInclude(Include.NON_NULL)
	private int assignedVehicle;
	
	@JsonInclude(Include.NON_NULL)	
	private List<PlannedStop> plannedStops;
	
	@JsonInclude(Include.NON_NULL)
	private String status; // Pendente, Em progresso, Concluída
	
	@JsonInclude(Include.NON_NULL)
	private PlannedStop longestStop;
	
	@JsonInclude(Include.NON_NULL)
	private PlannedStop currentStop;
	
	public Route() {}
	
	public Route(String routePlan) {
		
		this.routePlan = routePlan;
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoutePlan() {
		return routePlan;
	}

	public void setRoutePlan(String routePlan) {
		this.routePlan = routePlan;
	}

	public int getAssignedVehicle() {
		return assignedVehicle;
	}

	public void setAssignedVehicle(int assignedVehicle) {
		this.assignedVehicle = assignedVehicle;
	}

	public List<PlannedStop> getPlannedStops() {
		return plannedStops;
	}

	public void setPlannedStops(List<PlannedStop> plannedStops) {
		this.plannedStops = plannedStops;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PlannedStop getLongestStop() {
		return longestStop;
	}

	public void setLongestStop(PlannedStop longestStop) {
		this.longestStop = longestStop;
	}

	public PlannedStop getCurrentStop() {
		return currentStop;
	}

	public void setCurrentStop(PlannedStop currentStop) {
		this.currentStop = currentStop;
	}

}
