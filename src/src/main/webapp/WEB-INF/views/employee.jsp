 
<!-- /WEB-INF/views/layout/header.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Employee Portal</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <nav class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
                <div class="position-sticky pt-3">
                    <div class="text-center mb-4">
                        <h4>Employee Portal</h4>
                    </div>
                    <ul class="nav flex-column">
                        <li class="nav-item">
                            <a class="nav-link ${param.page == 'dashboard' ? 'active' : ''}" href="${pageContext.request.contextPath}/dashboard">
                                <i class="bi bi-house-door me-2"></i>Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link ${param.page == 'profile' ? 'active' : ''}" href="${pageContext.request.contextPath}/profile">
                                <i class="bi bi-person me-2"></i>My Profile
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link ${param.page == 'requests' ? 'active' : ''}" href="${pageContext.request.contextPath}/requests">
                                <i class="bi bi-file-text me-2"></i>Requests
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link ${param.page == 'performance' ? 'active' : ''}" href="${pageContext.request.contextPath}/performance">
                                <i class="bi bi-graph-up me-2"></i>Performance
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>

            <!-- Main content -->
            <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                    <h1>${param.title}</h1>
                    <div class="d-flex align-items-center">
                        <div class="position-relative me-3">
                            <i class="bi bi-bell fs-5"></i>
                            <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">2</span>
                        </div>
                        <div class="dropdown">
                            <button class="btn btn-link dropdown-toggle text-decoration-none" type="button" id="userMenu" data-bs-toggle="dropdown">
                                <img src="${pageContext.request.contextPath}/images/avatar.jpg" class="rounded-circle" width="32" height="32" alt="">
                                <span class="ms-2">${employee.fullName}</span>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-end">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout">Logout</a></li>
                            </ul>
                        </div>
                    </div>
                </div>

<!-- /WEB-INF/views/dashboard.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/WEB-INF/views/layout/header.jsp">
    <jsp:param name="title" value="Dashboard"/>
    <jsp:param name="page" value="dashboard"/>
</jsp:include>

<div class="row row-cols-1 row-cols-md-2 row-cols-lg-4 g-4 mb-4">
    <div class="col">
        <div class="card h-100">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-start">
                    <div>
                        <h6 class="card-subtitle mb-2 text-muted">Leave Balance</h6>
                        <h2 class="card-title mb-0">${employee.leaveBalance} days</h2>
                    </div>
                    <div class="rounded-circle bg-light p-2">
                        <i class="bi bi-calendar text-primary"></i>
                    </div>
                </div>
                <p class="card-text text-muted mt-3">Annual leave remaining</p>
            </div>
        </div>
    </div>
    
    <div class="col">
        <div class="card h-100">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-start">
                    <div>
                        <h6 class="card-subtitle mb-2 text-muted">Pending Requests</h6>
                        <h2 class="card-title mb-0">${employee.pendingRequests}</h2>
                    </div>
                    <div class="rounded-circle bg-light p-2">
                        <i class="bi bi-file-text text-warning"></i>
                    </div>
                </div>
                <p class="card-text text-muted mt-3">Awaiting approval</p>
            </div>
        </div>
    </div>

    <div class="col">
        <div class="card h-100">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-start">
                    <div>
                        <h6 class="card-subtitle mb-2 text-muted">Performance Score</h6>
                        <h2 class="card-title mb-0">${employee.performanceScore}%</h2>
                    </div>
                    <div class="rounded-circle bg-light p-2">
                        <i class="bi bi-graph-up text-success"></i>
                    </div>
                </div>
                <p class="card-text text-muted mt-3">Last evaluation score</p>
            </div>
        </div>
    </div>

    <div class="col">
        <div class="card h-100">
            <div class="card-body">
                <div class="d-flex justify-content-between align-items-start">
                    <div>
                        <h6 class="card-subtitle mb-2 text-muted">Upcoming Training</h6>
                        <h2 class="card-title mb-0">${employee.upcomingTrainings}</h2>
                    </div>
                    <div class="rounded-circle bg-light p-2">
                        <i class="bi bi-mortarboard text-info"></i>
                    </div>
                </div>
                <p class="card-text text-muted mt-3">Scheduled in next 30 days</p>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>

<!-- /WEB-INF/views/profile.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/WEB-INF/views/layout/header.jsp">
    <jsp:param name="title" value="My Profile"/>
    <jsp:param name="page" value="profile"/>
</jsp:include>

<div class="card">
    <div class="card-body">
        <form action="${pageContext.request.contextPath}/profile/update" method="post">
            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Full Name</label>
                    <input type="text" class="form-control" value="${employee.fullName}" readonly>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Email</label>
                    <input type="email" class="form-control" value="${employee.email}" readonly>
                </div>
            </div>
            
            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Department</label>
                    <input type="text" class="form-control" value="${employee.department}" readonly>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Position</label>
                    <input type="text" class="form-control" value="${employee.position}" readonly>
                </div>
            </div>
            
            <div class="mb-3">
                <label class="form-label">Address</label>
                <textarea class="form-control" name="address" rows="3">${employee.address}</textarea>
            </div>
            
            <div class="mb-3">
                <label class="form-label">Phone Number</label>
                <input type="tel" class="form-control" name="phone" value="${employee.phone}">
            </div>
            
            <button type="submit" class="btn btn-primary">Update Profile</button>
        </form>
    </div>
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>

<!-- /WEB-INF/views/layout/footer.jsp -->
            </main>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
</body>
</html>