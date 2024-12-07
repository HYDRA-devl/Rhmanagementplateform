<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

  <div class="container mx-auto mt-6">
    <div class="bg-white shadow rounded-lg p-6">
      <h2 class="text-2xl font-bold mb-4">Training Management</h2>

      <!-- Tabs -->
      <div class="border-b border-gray-200 mb-4">
        <nav class="-mb-px flex space-x-4" id="tabs">
          <button
            class="tab-link text-gray-600 border-b-2 border-transparent py-2 px-4 hover:text-blue-600"
            data-target="activeTrainings"
          >
            Active Trainings
          </button>
          <button
            class="tab-link text-gray-600 border-b-2 border-transparent py-2 px-4 hover:text-blue-600"
            data-target="newTraining"
          >
            New Training
          </button>
        </nav>
      </div>

      <!-- Tab Contents -->
      <div id="tabContent">
        <!-- Active Trainings Tab -->
        <div class="tab-panel" id="activeTrainings">
          <p class="text-gray-600">Content for Active Trainings</p>
          <div class="overflow-x-auto">
            <table class="table-auto w-full text-left border-collapse">
              <thead class="bg-gray-50">
                <tr>
                  <th class="px-4 py-2 border-b">Training Name</th>
                  <th class="px-4 py-2 border-b">Department</th>
                  <th class="px-4 py-2 border-b">Start Date</th>
                  <th class="px-4 py-2 border-b">End Date</th>
                  <th class="px-4 py-2 border-b">Participants</th>
                  <th class="px-4 py-2 border-b">Actions</th>
                </tr>
              </thead>
              <tbody>
                <!-- Example Data -->
                <tr>
                  <td class="px-4 py-2 border-b">React Basics</td>
                  <td class="px-4 py-2 border-b">IT</td>
                  <td class="px-4 py-2 border-b">2024-12-01</td>
                  <td class="px-4 py-2 border-b">2024-12-10</td>
                  <td class="px-4 py-2 border-b">30</td>
                  <td class="px-4 py-2 border-b">
                    <button class="text-blue-600 hover:underline">Edit</button> | 
                    <button class="text-red-600 hover:underline">Delete</button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <!-- New Training Tab -->
        <div class="tab-panel hidden" id="newTraining">
          <form class="space-y-4">
            <div class="grid grid-cols-2 gap-4">
              <div>
                <label for="trainingName" class="block text-gray-700">Training Name</label>
                <input
                  type="text"
                  id="trainingName"
                  name="name"
                  class="block w-full mt-1 border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
                  required
                />
              </div>
              <div>
                <label for="department" class="block text-gray-700">Department</label>
                <select
                  id="department"
                  name="department"
                  class="block w-full mt-1 border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
                  required
                >
                  <option value="">Select Department</option>
                  <option value="IT">IT</option>
                  <option value="HR">HR</option>
                  <option value="Finance">Finance</option>
                  <option value="Marketing">Marketing</option>
                </select>
              </div>
            </div>
            <div class="grid grid-cols-2 gap-4">
              <div>
                <label for="startDate" class="block text-gray-700">Start Date</label>
                <input
                  type="date"
                  id="startDate"
                  name="startDate"
                  class="block w-full mt-1 border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
                  required
                />
              </div>
              <div>
                <label for="endDate" class="block text-gray-700">End Date</label>
                <input
                  type="date"
                  id="endDate"
                  name="endDate"
                  class="block w-full mt-1 border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
                  required
                />
              </div>
            </div>
            <div>
              <label for="description" class="block text-gray-700">Description</label>
              <textarea
                id="description"
                name="description"
                rows="3"
                class="block w-full mt-1 border border-gray-300 rounded-md shadow-sm focus:ring-blue-500 focus:border-blue-500"
                required
              ></textarea>
            </div>
            <button type="submit" class="bg-blue-500 text-white py-2 px-4 rounded hover:bg-blue-600">
              Create Training
            </button>
          </form>
        </div>
      </div>
    </div>
  </div>

  <script>
    // JavaScript for Tabs
    document.addEventListener("DOMContentLoaded", () => {
      const tabs = document.querySelectorAll(".tab-link");
      const panels = document.querySelectorAll(".tab-panel");

      tabs.forEach((tab) => {
        tab.addEventListener("click", () => {
          // Remove active state from all tabs
          tabs.forEach((t) => t.classList.remove("border-blue-600", "text-blue-600"));
          // Hide all panels
          panels.forEach((panel) => panel.classList.add("hidden"));

          // Activate clicked tab
          tab.classList.add("border-blue-600", "text-blue-600");
          // Show associated panel
          const target = tab.getAttribute("data-target");
          document.getElementById(target).classList.remove("hidden");
        });
      });
    });
  </script>
