/**
 * 
 */
package fr.test.rtm.rtmpostgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author julien.aubert (AUBAY)
 * @date 1 févr. 2017
 * @version 1.0
 */
public class App {

	private final String url = "jdbc:postgresql://localhost:5432/rtm_01_adm";
	private final String user = "user";
	private final String password = "user123";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		App app = new App();
		app.connect();
		app.getUserCount();
		app.getVehicles();
		app.findVehicleById(10);
		//
		Vehicle newVehicle = new Vehicle("Test-insert", 12);
		long id = app.insertVehicle(newVehicle);
		System.out.println(String.format("%s, %d vehicle has been inserted with id %d", newVehicle.getVehicle_name(), newVehicle.getVehicle_body_type_id(), id));
		app.findVehicleById(id);
		List<Vehicle> liste = new ArrayList<Vehicle>();
		liste.add(new Vehicle("Test-insert2", 12));
		liste.add(new Vehicle("Test-insert3", 12));
		app.insertVehicles(liste);
	}

	public Connection connect() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, password);

			System.out.println("Connected to the PostgreSQL server successfully.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return conn;
	}

	/**
	 * Get users count
	 * 
	 * @return
	 */
	public int getUserCount() {
		String SQL = "SELECT count(*) FROM tb0031_user";
		int count = 0;

		try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(SQL)) {
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

		return count;
	}

	/**
	 * Get all rows in the tb0238_vehicle table
	 */
	public void getVehicles() {

		String SQL = "SELECT id, vehicle_name, vehicle_body_type_id FROM tb0238_vehicle";

		try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(SQL)) {
			// display Vehicle information
			displayVehicle(rs);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * Display Vehicle
	 *
	 * @param rs
	 * @throws SQLException
	 */
	private void displayVehicle(ResultSet rs) throws SQLException {
		while (rs.next()) {
			System.out.println(rs.getString("id") + "\t" + rs.getString("vehicle_name") + "\t" + rs.getString("vehicle_body_type_id"));

		}
	}

	/**
	 * Find Vehicle by his/her ID
	 *
	 * @param VehicleId
	 */
	public void findVehicleById(long vehicleId) {
		String SQL = "SELECT id, vehicle_name, vehicle_body_type_id " + "FROM tb0238_vehicle " + "WHERE id = ?";

		try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(SQL)) {

			pstmt.setLong(1, vehicleId);
			ResultSet rs = pstmt.executeQuery();
			displayVehicle(rs);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * insert vehicle
	 * 
	 * @param vehicle
	 * @return
	 */
	public long insertVehicle(Vehicle vehicle) {
		String SQL = "INSERT INTO tb0238_vehicle(vehicle_name, vehicle_body_type_id) " + "VALUES(?,?)";

		long id = 0;

		try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
			conn.setAutoCommit(false);
			pstmt.setString(1, vehicle.getVehicle_name());
			pstmt.setInt(2, vehicle.getVehicle_body_type_id());

			int affectedRows = pstmt.executeUpdate();
			// check the affected rows
			if (affectedRows > 0) {
				// get the ID back
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
					if (rs.next()) {
						id = rs.getLong(1);
					}
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
				}
				// conn.commit();
			} else {
				conn.rollback();
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return id;
	}

	/**
	 * insert multiple vehicles
	 */
	public void insertVehicles(List<Vehicle> list) {
		String SQL = "INSERT INTO tb0238_vehicle(vehicle_name, vehicle_body_type_id) " + "VALUES(?,?)";
		try (Connection conn = connect(); PreparedStatement statement = conn.prepareStatement(SQL);) {
			int count = 0;

			for (Vehicle vehicle : list) {
				statement.setString(1, vehicle.getVehicle_name());
				statement.setInt(2, vehicle.getVehicle_body_type_id());

				statement.addBatch();
				count++;
				// execute every 100 rows or less
				if (count % 100 == 0 || count == list.size()) {
					statement.executeBatch();
				}
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

}
