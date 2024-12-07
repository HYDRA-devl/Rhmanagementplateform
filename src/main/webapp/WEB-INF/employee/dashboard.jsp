<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Employee Dashboard</title>
  <script src="https://cdn.tailwindcss.com"></script>
  <style>
    .tab-active {
      background-color: rgb(37, 99, 235);
      color: white;
    }
  </style>
</head>
<body class="bg-gray-100 dark:bg-gray-900 h-screen flex">
  <aside class="w-64 bg-white dark:bg-gray-800 p-4">
    <h1 class="text-2xl font-bold mb-8 text-blue-600">Employee Portal</h1>
    <nav class="space-y-2">
      <button onclick="showTab('dashboard')" id="dashboard-tab" 
        class="w-full text-left p-3 flex items-center text-gray-700 hover:bg-blue-600 hover:text-white rounded tab-active">
        <span class="mr-2">🏠</span>
        Dashboard
      </button>
      <button onclick="showTab('profile')" id="profile-tab"
        class="w-full text-left p-3 flex items-center text-gray-700 hover:bg-blue-600 hover:text-white rounded">
        <span class="mr-2">👤</span>
        My Profile
      </button>
      <button onclick="showTab('requests')" id="requests-tab"
        class="w-full text-left p-3 flex items-center text-gray-700 hover:bg-blue-600 hover:text-white rounded">
        <span class="mr-2">📝</span>
        Requests
      </button>
      <button onclick="showTab('performance')" id="performance-tab"
        class="w-full text-left p-3 flex items-center text-gray-700 hover:bg-blue-600 hover:text-white rounded">
        <span class="mr-2">📊</span>
        Performance
      </button>
    </nav>
  </aside>

  <main class="flex-1 p-8">
    <header class="flex justify-between items-center mb-8">
      <h2 id="tab-title" class="text-3xl font-bold text-gray-900 dark:text-white">Dashboard</h2>
      <div class="flex items-center space-x-4">
        <button class="p-2 rounded-full hover:bg-gray-100">
          <span class="sr-only">Notifications</span>
          🔔
        </button>
        <div class="relative">
          <button class="h-8 w-8 rounded-full bg-gray-200 flex items-center justify-center">
            👤
          </button>
        </div>
      </div>
    </header>

    <!-- Dashboard View -->
    <div id="dashboard-content" class="tab-pane">
      <div class="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
        <div class="bg-white dark:bg-gray-800 p-6 rounded-lg shadow">
          <div class="flex justify-between items-center pb-2">
            <h3 class="text-sm font-medium text-gray-600">Leave Balance</h3>
            <span>📅</span>
          </div>
          <div class="text-2xl font-bold">${employee.soldeConge} days</div>
          <p class="text-xs text-gray-500">Annual leave remaining</p>
        </div>
        
        <div class="bg-white dark:bg-gray-800 p-6 rounded-lg shadow">
          <div class="flex justify-between items-center pb-2">
            <h3 class="text-sm font-medium text-gray-600">Salary</h3>
            <span>💰</span>
          </div>
          <div class="text-2xl font-bold">${employee.salaire} DH</div>
          <p class="text-xs text-gray-500">Current salary</p>
        </div>

        <div class="bg-white dark:bg-gray-800 p-6 rounded-lg shadow">
          <div class="flex justify-between items-center pb-2">
            <h3 class="text-sm font-medium text-gray-600">Performance Score</h3>
            <span>📈</span>
          </div>
          <div class="text-2xl font-bold">${employee.evaluationPerformance}/5</div>
          <p class="text-xs text-gray-500">Last evaluation score</p>
        </div>

        <div class="bg-white dark:bg-gray-800 p-6 rounded-lg shadow">
          <div class="flex justify-between items-center pb-2">
            <h3 class="text-sm font-medium text-gray-600">Employee ID</h3>
            <span>🪪</span>
          </div>
          <p class="text-xs text-gray-500">Employee identifier</p>
        </div>
      </div>
    </div>

    <!-- Profile View -->
    <div id="profile-content" class="tab-pane hidden">
      <div class="bg-white dark:bg-gray-800 p-6 rounded-lg shadow">
        <h3 class="text-lg font-semibold mb-4">My Profile</h3>
        <div class="grid grid-cols-2 gap-4">
          <div>
            <label class="block text-sm font-medium text-gray-600">Full Name</label>
            <input type="text" value="${employee.nom}" class="w-full p-2 mt-1 border rounded" readonly>
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-600">Employee ID</label>
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-600">Salary</label>
            <input type="text" value="${employee.salaire} DH" class="w-full p-2 mt-1 border rounded" readonly>
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-600">Leave Balance</label>
            <input type="text" value="${employee.soldeConge} days" class="w-full p-2 mt-1 border rounded" readonly>
          </div>
        </div>
        <button class="mt-6 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700">
          Update Profile
        </button>
      </div>
    </div>

    <!-- Requests View -->
    <div id="requests-content" class="tab-pane hidden">
      <div class="bg-white dark:bg-gray-800 p-6 rounded-lg shadow">
        <div class="border-b pb-4 mb-4">
          <div class="flex space-x-4">
            <button class="px-4 py-2 text-sm font-medium text-blue-600 border-b-2 border-blue-600">
              New Request
            </button>
            <button class="px-4 py-2 text-sm font-medium text-gray-500 hover:text-gray-700">
              Pending Requests
            </button>
            <button class="px-4 py-2 text-sm font-medium text-gray-500 hover:text-gray-700">
              Request History
            </button>
          </div>
        </div>
        <div class="space-y-4">
          <div>
            <label class="block text-sm font-medium text-gray-600">Request Type</label>
            <select class="w-full p-2 mt-1 border rounded">
              <option>Leave Request</option>
              <option>Other Request</option>
            </select>
          </div>
          <div class="grid grid-cols-2 gap-4">
            <div>
              <label class="block text-sm font-medium text-gray-600">Start Date</label>
              <input type="date" class="w-full p-2 mt-1 border rounded">
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-600">End Date</label>
              <input type="date" class="w-full p-2 mt-1 border rounded">
            </div>
          </div>
          <div>
            <label class="block text-sm font-medium text-gray-600">Request Details</label>
            <textarea class="w-full p-2 mt-1 border rounded" rows="4"></textarea>
          </div>
          <button class="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700">
            Submit Request
          </button>
        </div>
      </div>
    </div>

    <!-- Performance View -->
    <div id="performance-content" class="tab-pane hidden">
      <div class="bg-white dark:bg-gray-800 p-6 rounded-lg shadow">
        <div class="border-b pb-4 mb-4">
          <div class="flex space-x-4">
            <button class="px-4 py-2 text-sm font-medium text-blue-600 border-b-2 border-blue-600">
              Performance Overview
            </button>
          </div>
        </div>
        <div class="space-y-4">
          <div class="border rounded-lg p-4">
            <h4 class="text-lg font-semibold mb-2">Current Performance</h4>
            <p class="text-sm text-gray-600 mb-2">Overall Score: ${employee.evaluationPerformance}/5</p>
          </div>
        </div>
      </div>
    </div>
  </main>

  <script>
    function showTab(tabName) {
      // Update the header title
      document.getElementById('tab-title').textContent = 
        tabName.charAt(0).toUpperCase() + tabName.slice(1);

      // Hide all tab panes
      document.querySelectorAll('.tab-pane').forEach(pane => {
        pane.classList.add('hidden');
      });

      // Show the selected tab pane
      document.getElementById(tabName + '-content').classList.remove('hidden');

      // Update tab button styles
      document.querySelectorAll('button[id$="-tab"]').forEach(button => {
        button.classList.remove('tab-active');
      });
      document.getElementById(tabName + '-tab').classList.add('tab-active');
    }

    // Initialize dashboard as active tab
    document.addEventListener('DOMContentLoaded', function() {
      showTab('dashboard');
    });
  </script>
</body>
</html>