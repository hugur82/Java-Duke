import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import {
  getEmployeeById,
  resetEmployeePassword,
  updateEmployee,
  updateEmployeeStatus,
  type Employee,
  type EmployeeRole,
  type EmployeeStatus,
} from "../api/employeeApi";

function EmployeeDetails() {
  const { id } = useParams();
  const navigate = useNavigate();

  const employeeId = Number(id);

  const [employee, setEmployee] = useState<Employee | null>(null);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [editing, setEditing] = useState(false);

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [role, setRole] = useState<EmployeeRole>("EMPLOYEE");

  const [saving, setSaving] = useState(false);

  const [resetPassword, setResetPassword] = useState<string | null>(null);
  const [isResettingPassword, setIsResettingPassword] = useState(false);

  useEffect(() => {
    async function loadEmployee() {
      try {
        setLoading(true);
        setError("");

        const data = await getEmployeeById(employeeId);

        setEmployee(data);

        setFirstName(data.firstName);
        setLastName(data.lastName);
        setEmail(data.email);
        setRole(data.role);
      } catch {
        setError("Failed to load employee.");
      } finally {
        setLoading(false);
      }
    }

    if (!Number.isNaN(employeeId)) {
      loadEmployee();
    }
  }, [employeeId]);

  const handleEdit = () => {
    if (!employee) {
      return;
    }

    setFirstName(employee.firstName);
    setLastName(employee.lastName);
    setEmail(employee.email);
    setRole(employee.role);

    setEditing(true);
  };

  const handleCancel = () => {
    if (!employee) {
      return;
    }

    setFirstName(employee.firstName);
    setLastName(employee.lastName);
    setEmail(employee.email);
    setRole(employee.role);

    setEditing(false);
  };

  const handleSave = async () => {
    try {
      setSaving(true);
      setError("");

      const updatedEmployee = await updateEmployee(employeeId, {
        firstName,
        lastName,
        email,
        role,
      });

      setEmployee(updatedEmployee);
      setEditing(false);
    } catch {
      setError("Failed to update employee.");
    } finally {
      setSaving(false);
    }
  };

  const handleResetPassword = async () => {
    const confirmed = window.confirm(
      "Are you sure you want to reset this employee's password?",
    );

    if (!confirmed) {
      return;
    }

    try {
      setError("");
      setResetPassword(null);
      setIsResettingPassword(true);

      const newPassword = await resetEmployeePassword(employeeId);

      setResetPassword(newPassword);
    } catch (error) {
      if (error instanceof Error) {
        setError(error.message);
      } else {
        setError("Failed to reset employee password.");
      }
    } finally {
      setIsResettingPassword(false);
    }
  };

  const handleStatusChange = async (status: EmployeeStatus) => {
    try {
      setError("");

      const updatedEmployee = await updateEmployeeStatus(employeeId, status);

      setEmployee(updatedEmployee);
    } catch (error) {
      if (error instanceof Error) {
        setError(error.message);
      } else {
        setError("Failed to update employee status.");
      }
    }
  };

  if (loading) {
    return (
      <main className="flex-1 overflow-auto bg-gray-50 p-6 dark:bg-gray-950">
        <p className="text-gray-900 dark:text-white">Loading...</p>
      </main>
    );
  }

  if (error && !employee) {
    return (
      <main className="flex-1 overflow-auto bg-gray-50 p-6 dark:bg-gray-950">
        <button
          type="button"
          onClick={() => navigate("/employees")}
          className="mb-6 rounded-lg border border-gray-300 px-4 py-2 text-gray-900 hover:bg-gray-100 dark:border-gray-700 dark:text-white dark:hover:bg-gray-800"
        >
          ← Back to Employees
        </button>

        <p className="text-red-600 dark:text-red-400">{error}</p>
      </main>
    );
  }

  if (!employee) {
    return null;
  }

  return (
    <main className="flex-1 overflow-auto bg-gray-50 p-6 dark:bg-gray-950">
      <div className="mb-6 flex items-center justify-between">
        <button
          type="button"
          onClick={() => navigate("/employees")}
          className="rounded-lg border border-gray-300 px-4 py-2 text-gray-900 hover:bg-gray-100 dark:border-gray-700 dark:text-white dark:hover:bg-gray-800"
        >
          ← Back to Employees
        </button>

        {!editing && (
          <button
            type="button"
            onClick={handleEdit}
            className="rounded-lg bg-black px-4 py-2 text-white hover:bg-gray-800 dark:bg-white dark:text-black dark:hover:bg-gray-200"
          >
            Edit
          </button>
        )}
      </div>

      {error && (
        <div className="mb-6 rounded-lg border border-red-300 bg-red-50 p-4 text-red-700 dark:border-red-800 dark:bg-red-950 dark:text-red-300">
          {error}
        </div>
      )}

      <div className="rounded-lg border border-gray-200 bg-white p-6 dark:border-gray-800 dark:bg-gray-900">
        <h1 className="mb-6 text-2xl font-bold text-gray-900 dark:text-white">
          Employee Details
        </h1>

        {editing ? (
          <div className="space-y-5">
            <div>
              <label className="mb-1 block text-sm font-medium text-gray-900 dark:text-gray-300">
                First name
              </label>

              <input
                value={firstName}
                onChange={(event) => setFirstName(event.target.value)}
                className="w-full rounded-lg border border-gray-300 px-4 py-2 text-gray-900 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
              />
            </div>

            <div>
              <label className="mb-1 block text-sm font-medium text-gray-900 dark:text-gray-300">
                Last name
              </label>

              <input
                value={lastName}
                onChange={(event) => setLastName(event.target.value)}
                className="w-full rounded-lg border border-gray-300 px-4 py-2 text-gray-900 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
              />
            </div>

            <div>
              <label className="mb-1 block text-sm font-medium text-gray-900 dark:text-gray-300">
                Email
              </label>

              <input
                type="email"
                value={email}
                onChange={(event) => setEmail(event.target.value)}
                className="w-full rounded-lg border border-gray-300 px-4 py-2 text-gray-900 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
              />
            </div>

            <div>
              <label className="mb-1 block text-sm font-medium text-gray-900 dark:text-gray-300">
                Role
              </label>

              <select
                value={role}
                onChange={(event) =>
                  setRole(event.target.value as EmployeeRole)
                }
                className="w-full rounded-lg border border-gray-300 px-4 py-2 text-gray-900 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
              >
                <option value="EMPLOYEE">EMPLOYEE</option>
                <option value="ADMIN">ADMIN</option>
              </select>
            </div>

            <div className="flex gap-3">
              <button
                type="button"
                onClick={handleSave}
                disabled={saving}
                className="rounded-lg bg-black px-4 py-2 text-white hover:bg-gray-800 disabled:opacity-50 dark:bg-white dark:text-black dark:hover:bg-gray-200"
              >
                {saving ? "Saving..." : "Save"}
              </button>

              <button
                type="button"
                onClick={handleCancel}
                className="rounded-lg border border-gray-300 px-4 py-2 text-gray-900 hover:bg-gray-100 dark:border-gray-700 dark:text-white dark:hover:bg-gray-800"
              >
                Cancel
              </button>
            </div>
          </div>
        ) : (
          <div className="space-y-4">
            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">
                First name
              </p>

              <p className="text-lg text-gray-900 dark:text-white">
                {employee.firstName}
              </p>
            </div>

            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">
                Last name
              </p>

              <p className="text-lg text-gray-900 dark:text-white">
                {employee.lastName}
              </p>
            </div>

            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">Email</p>

              <p className="text-lg text-gray-900 dark:text-white">
                {employee.email}
              </p>
            </div>

            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">Role</p>

              <p className="text-lg text-gray-900 dark:text-white">
                {employee.role}
              </p>
            </div>

            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">Status</p>

              <p className="text-lg text-gray-900 dark:text-white">
                {employee.status}
              </p>
            </div>

            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">
                Created at
              </p>

              <p className="text-lg text-gray-900 dark:text-white">
                {new Date(employee.createdAt).toLocaleString()}
              </p>
            </div>

            <div>
              <p className="text-sm text-gray-500 dark:text-gray-400">
                Last login
              </p>

              <p className="text-lg text-gray-900 dark:text-white">
                {employee.lastLoginAt
                  ? new Date(employee.lastLoginAt).toLocaleString()
                  : "Never"}
              </p>
            </div>

            <div className="pt-4">
              <p className="mb-2 text-sm font-medium text-gray-900 dark:text-gray-300">
                Status
              </p>

              <div className="flex gap-3">
                <button
                  type="button"
                  onClick={() => handleStatusChange("ACTIVE")}
                  disabled={employee.status === "ACTIVE"}
                  className="rounded-lg border border-gray-300 px-4 py-2 text-gray-900 hover:bg-gray-100 disabled:cursor-not-allowed disabled:opacity-40 dark:border-gray-700 dark:text-white dark:hover:bg-gray-800"
                >
                  Activate
                </button>

                <button
                  type="button"
                  onClick={() => handleStatusChange("DISABLED")}
                  disabled={employee.status === "DISABLED"}
                  className="rounded-lg border border-gray-300 px-4 py-2 text-gray-900 hover:bg-gray-100 disabled:cursor-not-allowed disabled:opacity-40 dark:border-gray-700 dark:text-white dark:hover:bg-gray-800"
                >
                  Disable
                </button>
              </div>
            </div>

            <div className="pt-4">
              <p className="mb-2 text-sm font-medium text-gray-900 dark:text-gray-300">
                Password
              </p>

              <button
                type="button"
                onClick={handleResetPassword}
                disabled={isResettingPassword}
                className="rounded-lg border border-gray-300 px-4 py-2 text-gray-900 hover:bg-gray-100 disabled:cursor-not-allowed disabled:opacity-50 dark:border-gray-700 dark:text-white dark:hover:bg-gray-800"
              >
                {isResettingPassword ? "Resetting..." : "Reset Password"}
              </button>

              {resetPassword && (
                <div className="mt-4 rounded-lg border border-yellow-300 bg-yellow-50 p-4 dark:border-yellow-700 dark:bg-yellow-950">
                  <p className="mb-2 text-sm font-medium text-yellow-800 dark:text-yellow-300">
                    New password
                  </p>

                  <div className="flex items-center gap-3">
                    <code className="rounded bg-white px-3 py-2 font-mono text-sm text-gray-900 dark:bg-gray-900 dark:text-white">
                      {resetPassword}
                    </code>

                    <button
                      type="button"
                      onClick={() =>
                        navigator.clipboard.writeText(resetPassword)
                      }
                      className="rounded-lg border border-gray-300 px-3 py-2 text-sm text-gray-900 hover:bg-white dark:border-gray-700 dark:text-white dark:hover:bg-gray-900"
                    >
                      Copy
                    </button>
                  </div>

                  <p className="mt-2 text-xs text-yellow-700 dark:text-yellow-400">
                    Make sure to save this password before leaving this page.
                  </p>
                </div>
              )}
            </div>
          </div>
        )}
      </div>
    </main>
  );
}

export default EmployeeDetails;
