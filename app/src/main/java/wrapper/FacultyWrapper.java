package wrapper;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by student on 08-04-2016.
 */
public class FacultyWrapper {

    private String first_name = "";
    private String last_name = "";
    private String photo = "";
    private String department = "";
    private String research_area = "";
    private ArrayList<String> interest_areas = new ArrayList<String>();
    private ArrayList<String> contact_details = new ArrayList<String>();

    public String getFirst_name() {
        return first_name;
    }
    public String getLast_name() {
        return last_name;
    }
    public String getPhoto() {
        return photo;
    }
    public String getDepartment() {
        return department;
    }
    public String getResearch_area() {
        return research_area;
    }
    public ArrayList getInterest_areas() {
        return interest_areas;
    }
    public ArrayList getContact_details() {
        return contact_details;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public void setResearch_area(String research_area) {
        this.research_area = research_area;
    }
    public void setInterest_areas(ArrayList<String> interest_areas) {
        this.interest_areas = interest_areas;
    }
    public void setContact_details(ArrayList contact_details) {
        this.contact_details = contact_details;
    }

    public FacultyWrapper(JSONObject jObj) {
        try {
            first_name = jObj.getString("first_name");
            last_name = jObj.getString("last_name");
            photo = jObj.getString("photo");
            department = jObj.getString("department");
            research_area = jObj.getString("research_area");
            JSONObject interest_areas = jObj.getJSONObject("interest_areas");
            JSONObject contact_details = jObj.getJSONObject("contact_details");
            this.interest_areas.add(interest_areas.getString("subject_1"));
            this.interest_areas.add(interest_areas.getString("subject_2"));
            this.interest_areas.add(interest_areas.getString("subject_3"));
            this.contact_details.add(contact_details.getString("phone"));
            this.contact_details.add(contact_details.getString("email"));
        } catch (Exception e) {
        }
    }
}


