/**
 * Abstract class Users and WebSites
 * @author Jason Zhang
 * @version 1.0
 * @since 2022-02-17
 * 
 * You are writing the application which collects information about what sites were visited by what users.
 * There are three classes: User, WebSite and Visit. Many fields and methods of these classes are the same.
 *
 * Write a new base abstract class named BaseEntity. Provided classes must extend it. Move all repeating fields
 * and methods to the new class.
 *
 * After your modifications, the following code must work correctly:
 *
 * User user = new User();
 * user.setName("John Grant");
 *
 * BaseEntity userEntity = user;
 * userEntity.setId(100);
 * userEntity.setVersion(1);
 *
 * WebSite site = new WebSite();
 * site.setUrl("https://hyperskill.org");
 *
 * BaseEntity siteEntity = site;
 * siteEntity.setId(101);
 * siteEntity.setVersion(1);
 *
 * Visit visit = new Visit();
 * visit.setUser(user);
 * visit.setSite(site);
 *
 * BaseEntity baseVisit = visit;
 * baseVisit.setId(102);
 * baseVisit.setVersion(103);
 */

abstract class BaseEntity {
    protected long id;
    protected long version;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

}

class User extends BaseEntity {
    protected String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

class Visit extends BaseEntity {

    protected User user;
    protected WebSite site;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public WebSite getSite() {
        return site;
    }
    public void setSite(WebSite site) {
        this.site = site;
    }
}

class WebSite extends BaseEntity {

    protected String url;

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}