<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manager Dashboard</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        .tab-active {
            background-color: rgb(59, 130, 246);
            color: white;
        }
        .stat-card {
            transition: transform 0.2s;
        }
        .stat-card:hover {
            transform: translateY(-5px);
        }
        .sidebar-btn {
            transition: all 0.3s;
        }
        .sidebar-btn:hover {
            background-color: rgb(59, 130, 246);
            color: white;
        }
    </style>
</head>

<body class="bg-gray-50">
    <div class="flex h-screen">
        <!-- Sidebar -->
        <aside class="w-64 bg-white shadow-lg">
            <div class="p-6">
                <h1 class="text-2xl font-bold text-gray-800">Manager Portal</h1>
                <p class="text-sm text-gray-600 mt-1">Team Management</p>
            </div>
            <nav class="mt-4 px-4">
                <button onclick="switchTab('dashboard')" id="dashboard-tab" 
                    class="sidebar-btn w-full text-left px-4 py-3 rounded-lg flex items-center space-x-3 mb-2">
                    <i class="fas fa-chart-pie text-gray-600"></i>
                    <span>Dashboard</span>
                </button>
                <button onclick="switchTab('team')" id="team-tab"
                    class="sidebar-btn w-full text-left px-4 py-3 rounded-lg flex items-center space-x-3 mb-2">
                    <i class="fas fa-users text-gray-600"></i>
                    <span>Team Management</span>
                </button>
                <button onclick="switchTab('performance')" id="performance-tab"
                    class="sidebar-btn w-full text-left px-4 py-3 rounded-lg flex items-center space-x-3 mb-2">
                    <i class="fas fa-chart-line text-gray-600"></i>
                    <span>Performance Reviews</span>
                </button>
                <button onclick="switchTab('reports')" id="reports-tab"
                    class="sidebar-btn w-full text-left px-4 py-3 rounded-lg flex items-center space-x-3 mb-2">
                    <i class="fas fa-file-alt text-gray-600"></i>
                    <span>Reports</span>
                </button>
            </nav>
        </aside>

        <!-- Main Content -->
        <main class="flex-1 overflow-x-hidden overflow-y-auto bg-gray-50">
            <!-- Header -->
            <header class="bg-white shadow-sm">
                <div class="flex items-center justify-between px-8 py-5">
                    <h2 id="pageTitle" class="text-2xl font-semibold text-gray-800">Dashboard</h2>
                    <div class="flex items-center space-x-4">
                        <button class="p-2 text-gray-600 hover:bg-gray-100 rounded-full relative">
                            <i class="fas fa-bell"></i>
                            <span class="absolute top-0 right-0 h-2 w-2 bg-red-500 rounded-full"></span>
                        </button>
                        <div class="relative">
                            <button onclick="toggleUserMenu()" class="flex items-center space-x-3 focus:outline-none">
                                <img class="h-8 w-8 rounded-full object-cover border-2 border-gray-200" 
                                    src="https://ui-avatars.com/api/?name=Manager&background=0D8ABC&color=fff" 
                                    alt="Manager">
                                <span class="text-sm font-medium text-gray-700">${manager.nom}</span>
                                <i class="fas fa-chevron-down text-gray-600"></i>
                            </button>
                        </div>
                    </div>
                </div>
            </header>

            <!-- Dashboard Content -->
            <div id="dashboard-content" class="tab-content p-8">
                <!-- Stats Grid -->
                <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
                    <div class="stat-card bg-white rounded-lg shadow-sm p-6 border border-gray-100">
                        <div class="flex items-center justify-between">
                            <div>
                                <p class="text-sm font-medium text-gray-600">Team Size</p>
                                <p class="text-2xl font-bold text-gray-800 mt-2">${manager.teamSize}</p>
                            </div>
                            <div class="h-12 w-12 bg-blue-100 rounded-full flex items-center justify-center">
                                <i class="fas fa-users text-blue-600 text-xl"></i>
                            </div>
                        </div>
                    </div>

                    <div class="stat-card bg-white rounded-lg shadow-sm p-6 border border-gray-100">
                        <div class="flex items-center justify-between">
                            <div>
                                <p class="text-sm font-medium text-gray-600">Pending Approvals</p>
                                <p class="text-2xl font-bold text-gray-800 mt-2">${manager.pendingApprovals}</p>
                            </div>
                            <div class="h-12 w-12 bg-yellow-100 rounded-full flex items-center justify-center">
                                <i class="fas fa-clock text-yellow-600 text-xl"></i>
                            </div>
                        </div>
                    </div>

                    <div class="stat-card bg-white rounded-lg shadow-sm p-6 border border-gray-100">
                        <div class="flex items-center justify-between">
                            <div>
                                <p class="text-sm font-medium text-gray-600">Department Budget</p>
                                <p class="text-2xl font-bold text-gray-800 mt-2">${manager.budget} DH</p>
                            </div>
                            <div class="h-12 w-12 bg-green-100 rounded-full flex items-center justify-center">
                                <i class="fas fa-money-bill-wave text-green-600 text-xl"></i>
                            </div>
                        </div>
                    </div>

                    <div class="stat-card bg-white rounded-lg shadow-sm p-6 border border-gray-100">
                        <div class="flex items-center justify-between">
                            <div>
                                <p class="text-sm font-medium text-gray-600">Team Performance</p>
                                <p class="text-2xl font-bold text-gray-800 mt-2">${manager.teamPerformance}%</p>
                            </div>
                            <div class="h-12 w-12 bg-purple-100 rounded-full flex items-center justify-center">
                                <i class="fas fa-chart-line text-purple-600 text-xl"></i>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Team Members Table -->
                <div class="bg-white rounded-lg shadow-sm border border-gray-100">
                    <div class="px-6 py-4 border-b border-gray-100">
                        <h3 class="text-lg font-semibold text-gray-800">Team Members</h3>
                    </div>
                    <div class="overflow-x-auto">
                        <table class="min-w-full">
                            <thead>
                                <tr class="bg-gray-50">
                                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Employee</th>
                                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Role</th>
                                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Performance</th>
                                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
                                </tr>
                            </thead>
                            <tbody class="bg-white divide-y divide-gray-100">
                                <c:forEach items="${manager.teamMembers}" var="member">
                                    <tr class="hover:bg-gray-50">
                                        <td class="px-6 py-4 whitespace-nowrap">
                                            <div class="flex items-center">
                                                <img class="h-8 w-8 rounded-full" 
                                                    src="https://ui-avatars.com/api/?name=${member.name}&background=random" 
                                                    alt="${member.name}">
                                                <div class="ml-4">
                                                    <div class="text-sm font-medium text-gray-900">${member.name}</div>
                                                    <div class="text-sm text-gray-500">${member.email}</div>
                                                </div>
                                            </div>
                                        </td>
                                        <td class="px-6 py-4 whitespace-nowrap">
                                            <div class="text-sm text-gray-900">${member.role}</div>
                                        </td>
                                        <td class="px-6 py-4 whitespace-nowrap">
                                            <div class="flex items-center">
                                                <div class="w-16 bg-gray-200 rounded-full h-2 mr-2">
                                                    <div class="bg-blue-600 h-2 rounded-full" style="width: ${member.performance}%"></div>
                                                </div>
                                                <span class="text-sm text-gray-600">${member.performance}%</span>
                                            </div>
                                        </td>
                                        <td class="px-6 py-4 whitespace-nowrap text-sm">
                                            <button class="text-blue-600 hover:text-blue-900 mr-3">
                                                <i class="fas fa-edit"></i>
                                            </button>
                                            <button class="text-green-600 hover:text-green-900 mr-3">
                                                <i class="fas fa-chart-bar"></i>
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <!-- Team Management Content -->
            <div id="team-content" class="tab-content hidden p-8">
                <div class="bg-white rounded-lg shadow-sm p-6">
                    <div class="flex justify-between items-center mb-6">
                        <h3 class="text-lg font-semibold text-gray-800">Team Management</h3>
                        <button class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700">
                            <i class="fas fa-plus mr-2"></i>Add Member
                        </button>
                    </div>
                    <!-- Add your team management content here -->
                </div>
            </div>

            <!-- Performance Reviews Content -->
            <div id="performance-content" class="tab-content hidden p-8">
                <div class="bg-white rounded-lg shadow-sm p-6">
                    <h3 class="text-lg font-semibold text-gray-800 mb-6">Performance Reviews</h3>
                    <!-- Add your performance reviews content here -->
                </div>
            </div>

            <!-- Reports Content -->
            <div id="reports-content" class="tab-content hidden p-8">
                <div class="bg-white rounded-lg shadow-sm p-6">
                    <h3 class="text-lg font-semibold text-gray-800 mb-6">Reports</h3>
                    <!-- Add your reports content here -->
                </div>
            </div>
        </main>
    </div>

    <script>
        function switchTab(tabName) {
            // Hide all tab contents
            document.querySelectorAll('.tab-content').forEach(content => {
                content.classList.add('hidden');
            });
            
            // Show selected tab content
            document.getElementById(tabName + '-content').classList.remove('hidden');
            
            // Update page title
            document.getElementById('pageTitle').textContent = 
                tabName.charAt(0).toUpperCase() + tabName.slice(1);
            
            // Update active tab styling
            document.querySelectorAll('.sidebar-btn').forEach(btn => {
                btn.classList.remove('bg-blue-500', 'text-white');
            });
            document.getElementById(tabName + '-tab').classList.add('bg-blue-500', 'text-white');
        }

        function toggleUserMenu() {
            // Add user menu toggle logic here
        }

        // Initialize dashboard as active tab
        document.addEventListener('DOMContentLoaded', function() {
            switchTab('dashboard');
        });
    </script>
</body>
</html>