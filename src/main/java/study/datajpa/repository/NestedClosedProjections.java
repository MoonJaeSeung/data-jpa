package study.datajpa.repository;

public interface NestedClosedProjections {

    String getUsername();
    TeamaInfo getTeam();

    interface TeamaInfo{
        String getName();
    }
}
