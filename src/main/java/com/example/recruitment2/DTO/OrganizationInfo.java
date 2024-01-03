package com.example.recruitment2.DTO;

import com.example.recruitment2.Entity.Enum.EOrganizationStatus;
import com.example.recruitment2.Entity.Organization;
import com.example.recruitment2.Entity.User;
import lombok.Data;

@Data
public class OrganizationInfo {
    private String id;
    private String name;
    private String avatar;
    private EOrganizationStatus status;
    private int rate;
    private int followerCount;

    public static OrganizationInfo of(Organization organization){
        OrganizationInfo organizationInfo = new OrganizationInfo();
        organizationInfo.setId(organization.getId());
        organizationInfo.setName(organization.getName());
        organizationInfo.setAvatar(organization.getAvatar());
        organizationInfo.setStatus(organization.getStatus());
        organizationInfo.setRate(
            (organization.getReviews() != null && organization.getReviews().size() > 0) ?
                organization.getReviews().stream()
                    .map(review -> review.getRate())
                    .reduce(0, (rate, sum) -> sum + rate)/organization.getReviews().size()
            : 0
        );
        organizationInfo.setFollowerCount(organization.getFollowers() != null ? organization.getFollowers().size() : 0);
        return organizationInfo;
    }
}
