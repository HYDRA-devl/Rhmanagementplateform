<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="bg-white rounded-lg shadow p-6 ">
    <div class="mb-6">
        <h3 class="text-lg font-semibold mb-2">Account Management</h3>
        <p class="text-sm text-gray-500">Manage employee accounts and access</p>
    </div>

    <!-- Tabs -->
    <div class="border-b mb-4">
        <div class="flex space-x-4">
            <button onclick="showAccountTab('all')" class="px-4 py-2 border-b-2 border-blue-500">
                All Accounts
            </button>
            <button onclick="showAccountTab('new')" class="px-4 py-2 border-b-2 border-transparent">
                New Account
            </button>
        </div>
    </div>

    <!-- All Accounts Tab -->
    <div id="all-accounts" class="account-tab">
        <div class="flex items-center space-x-2 mb-4">
            <input type="text" placeholder="Search accounts..." 
                   class="px-4 py-2 border rounded-lg flex-1 max-w-sm">
            <button class="px-4 py-2 bg-blue-500 text-white rounded-lg">Search</button>
        </div>

        <table class="min-w-full h-20 overflow-y-scroll">
            <thead>
                <tr class="bg-gray-50">
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Email</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Department</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="account" items="${accounts}">
                    <tr>
                        <td class="px-6 py-4">${account.nom}</td>
                        <td class="px-6 py-4">${account.email}</td>
                        <td class="px-6 py-4">${account.departement}</td>
                        <td class="px-6 py-4">
                            <span class="px-2 py-1 text-sm rounded-full bg-green-100 text-green-800">
                                ${account.statusDisplay}
                            </span>
                        </td>
                        <td class="px-6 py-4">
                            <button class="text-blue-500 hover:text-blue-700 mr-2">Edit</button>
                            <button class="text-red-500 hover:text-red-700">Deactivate</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- New Account Tab -->
   <div id="new-accounts" class="account-tab hidden">
<form id="createUserForm" action="http://localhost:8080/RHproject/admin/createUser" method="POST" class="space-y-6">
        <input type="hidden" name="action" value="createUser">
        
        <div class="grid grid-cols-2 gap-6">
            <!-- Personal Information -->
            <div>
                <label class="block text-sm font-medium text-gray-700">Full Name</label>
                <input type="text" name="nom" required
                       class="mt-1 block w-full px-3 py-2 border rounded-lg focus:ring-blue-500 focus:border-blue-500">
            </div>
            
            <div>
                <label class="block text-sm font-medium text-gray-700">Email</label>
                <input type="email" name="email" required
                       class="mt-1 block w-full px-3 py-2 border rounded-lg focus:ring-blue-500 focus:border-blue-500">
            </div>
            
            <div>
                <label class="block text-sm font-medium text-gray-700">Password</label>
                <input type="password" name="motDePasse" required minlength="6"
                       class="mt-1 block w-full px-3 py-2 border rounded-lg focus:ring-blue-500 focus:border-blue-500">
                <p class="mt-1 text-sm text-gray-500">Minimum 6 characters</p>
            </div>
            
            <div>
                <label class="block text-sm font-medium text-gray-700">Role</label>
                <select name="role" required
                        class="mt-1 block w-full px-3 py-2 border rounded-lg focus:ring-blue-500 focus:border-blue-500">
                    <option value="">Select a role</option>
                    <option value="EMPLOYEE">Employee</option>
                    <option value="MANAGER">Manager</option>
                    <option value="ADMIN">Administrator</option>
                </select>
            </div>

            <!-- Additional Information -->
            <div>
                <label class="block text-sm font-medium text-gray-700">Department</label>
                <input type="text" name="departement"
                       class="mt-1 block w-full px-3 py-2 border rounded-lg focus:ring-blue-500 focus:border-blue-500">
            </div>
            
            <div>
                <label class="block text-sm font-medium text-gray-700">Team</label>
                <input type="text" name="equipe"
                       class="mt-1 block w-full px-3 py-2 border rounded-lg focus:ring-blue-500 focus:border-blue-500">
            </div>

            <!-- Employee-specific fields (shown only when role is EMPLOYEE) -->
            <div class="employee-field hidden">
                <label class="block text-sm font-medium text-gray-700">Salary</label>
                <input type="number" name="salaire" step="0.01"
                       class="mt-1 block w-full px-3 py-2 border rounded-lg focus:ring-blue-500 focus:border-blue-500">
            </div>
            
            <div class="employee-field hidden">
                <label class="block text-sm font-medium text-gray-700">Leave Balance</label>
                <input type="number" name="soldeConge"
                       class="mt-1 block w-full px-3 py-2 border rounded-lg focus:ring-blue-500 focus:border-blue-500">
            </div>
        </div>

        <div class="flex items-center justify-end space-x-4 mt-6">
            <button type="button" 
                    onclick="showAccountTab('all')"
                    class="px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50">
                Cancel
            </button>
            <button type="submit" 
                    class="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2">
                Create Account
            </button>
            <p>${pageContext.request.contextPath}</p>
            
        </div>
    </form>
</div>
</div>

<script>
    function showAccountTab(tab) {
        document.querySelectorAll('.account-tab').forEach(content => {
            content.classList.add('hidden');
        });
        document.getElementById(tab + '-accounts').classList.remove('hidden');
    }
    
    
    document.querySelector('select[name="role"]').addEventListener('change', function(e) {
        const employeeFields = document.querySelectorAll('.employee-field');
        employeeFields.forEach(field => {
            field.classList.toggle('hidden', e.target.value !== 'EMPLOYEE');
        });
    });

    // Form submission handling
   
</script>