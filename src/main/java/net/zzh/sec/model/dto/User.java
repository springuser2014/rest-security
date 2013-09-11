package net.zzh.sec.model.dto;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import net.zzh.common.persistence.model.INameableEntity;
import net.zzh.sec.model.Principal;
import net.zzh.sec.model.Role;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XmlRootElement
@XStreamAlias("user")
public class User implements INameableEntity {

    @XStreamAsAttribute
    private Long id;

    private String name;
    private String password;

    /* Marshalling */
    // - note: this gets rid of the collection entirely
    @XStreamImplicit
    // - note: this requires: xstream.addDefaultImplementation( java.util.HashSet.class, PersistentSet.class );
    // @XStreamConverter( value = HibernateCollectionConverter.class )
    private Set<Role> roles;

    public User() {
        super();
    }

    public User(final String nameToSet, final String passwordToSet, final Set<Role> rolesToSet) {
        super();

        name = nameToSet;
        password = passwordToSet;
        roles = rolesToSet;
    }

    public User(final Principal principal) {
        super();

        name = principal.getName();
        roles = principal.getRoles();
        id = principal.getId();
    }

    // API

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(final Long idToSet) {
        id = idToSet;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(final String nameToSet) {
        name = nameToSet;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String passwordToSet) {
        password = passwordToSet;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(final Set<Role> rolesToSet) {
        roles = rolesToSet;
    }

    //

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final User other = (User) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("name", name).toString();
    }

}
