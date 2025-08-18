package in.ashar.filehandling.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Place {
    public String officename;
    public String pincode; // Use String if leading zeros possible
    public String officeType;
    public String Deliverystatus;
    public String divisionname;
    public String regionname;
    public String circlename;
    public String Taluk;
    public String Districtname;
    public String statename;
    public String Telephone;
    public String relatedSuboffice;
    public String relatedHeadoffice;
}