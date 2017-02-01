/**
 * 
 */
package fr.test.rtm.rtmpostgresql;

/**
 * @author julien.aubert (AUBAY)
 * @date 1 févr. 2017
 * @version 1.0
 */
public class Vehicle {

	private String vehicle_name;

	private int vehicle_body_type_id;

	/**
	 * @param vehicle_name
	 * @param vehicle_body_type_id
	 */
	public Vehicle(String vehicle_name, int vehicle_body_type_id) {
		super();
		this.vehicle_name = vehicle_name;
		this.vehicle_body_type_id = vehicle_body_type_id;
	}

	/**
	 * @return the vehicle_name
	 */
	public String getVehicle_name() {
		return vehicle_name;
	}

	/**
	 * @param vehicle_name the vehicle_name to set
	 */
	public void setVehicle_name(String vehicle_name) {
		this.vehicle_name = vehicle_name;
	}

	/**
	 * @return the vehicle_body_type_id
	 */
	public int getVehicle_body_type_id() {
		return vehicle_body_type_id;
	}

	/**
	 * @param vehicle_body_type_id the vehicle_body_type_id to set
	 */
	public void setVehicle_body_type_id(int vehicle_body_type_id) {
		this.vehicle_body_type_id = vehicle_body_type_id;
	}

}
