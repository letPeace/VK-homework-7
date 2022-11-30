package login;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.util.security.Constraint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class SecurityHandlerBuilder {

    private static final String MANAGER_ROLE = "manager";
    private static final String GUEST_ROLE = "guest";
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";

    private final ConstraintSecurityHandler security = new ConstraintSecurityHandler();

    public ConstraintSecurityHandler build(LoginService loginService) {
        security.setLoginService(loginService);

        final List<ConstraintMapping> constraintMappings = new ArrayList<>();

        constraintMappings.addAll(constraintGetMapping(
                buildConstraint(MANAGER_ROLE),
                Collections.singletonList(LoginJDBC.MANAGER_FOLDER+LoginJDBC.MANAGER_FOLDER)
        ));

        constraintMappings.addAll(constraintPostMapping(
                buildConstraint(MANAGER_ROLE),
                Collections.singletonList(LoginJDBC.MANAGER_FOLDER+LoginJDBC.MANAGER_FOLDER)
        ));

        constraintMappings.addAll(constraintGetMapping(
                buildConstraint(MANAGER_ROLE, GUEST_ROLE),
                Collections.singletonList(LoginJDBC.GUEST_FOLDER)
        ));

        security.setConstraintMappings(constraintMappings);
        security.setAuthenticator(new BasicAuthenticator());
        security.setDenyUncoveredHttpMethods(true);
        return security;
    }

    private static Constraint buildConstraint(String... userRoles) {
        final Constraint starterConstraint = new Constraint();
        starterConstraint.setName(Constraint.__BASIC_AUTH);
        starterConstraint.setRoles(userRoles);
        starterConstraint.setAuthenticate(true);
        return starterConstraint;
    }

    private static Collection<ConstraintMapping> constraintGetMapping(Constraint constraint,
                                                                      Collection<String> paths) {
        return constraintMapping(constraint, paths, METHOD_GET);
    }

    private static Collection<ConstraintMapping> constraintPostMapping(Constraint constraint,
                                                                      Collection<String> paths) {
        return constraintMapping(constraint, paths, METHOD_POST);
    }

    private static Collection<ConstraintMapping> constraintMapping(Constraint constraint,
                                                                   Collection<String> paths,
                                                                   String method) {
        return paths.stream()
                .map(path -> {
                            final ConstraintMapping mapping = new ConstraintMapping();
                            mapping.setConstraint(constraint);
                            mapping.setPathSpec(path);
                            mapping.setMethod(method);
                            return mapping;
                        }
                ).collect(Collectors.toList());
    }
}