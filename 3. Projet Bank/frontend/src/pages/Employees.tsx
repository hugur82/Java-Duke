import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  createEmployee,
  getEmployees,
  type Employee,
  type EmployeeCreateRequest,
  type EmployeeRole,
} from "../api/employeeApi";

function Employees() {
  const navigate = useNavigate();

  const [employees, setEmployees] = useState<Employee[]>([]);
  const [firstNameSearch, setFirstNameSearch] = useState("");
  const [lastNameSearch, setLastNameSearch] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [showCreateForm, setShowCreateForm] = useState(false);

  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState<EmployeeRole>("EMPLOYEE");

  const [creating, setCreating] = useState(false);
  const [createError, setCreateError] = useState("");

  useEffect(() => {
    async function loadEmployees() {
      try {
        setLoading(true);
        setError("");

        const data = await getEmployees();
        setEmployees(data);
      } catch (error) {
        if (error instanceof Error) {
          setError(error.message);
        } else {
          setError("Failed to load employees.");
        }
      } finally {
        setLoading(false);
      }
    }

    loadEmployees();
  }, []);

  const filteredEmployees = employees.filter((employee) => {
    const firstNameMatch = employee.firstName
      .toLowerCase()
      .includes(firstNameSearch.toLowerCase());

    const lastNameMatch = employee.lastName
      .toLowerCase()
      .includes(lastNameSearch.toLowerCase());

    return firstNameMatch && lastNameMatch;
  });

  const handleCreateEmployee = async () => {
    if (
      !firstName.trim() ||
      !lastName.trim() ||
      !email.trim() ||
      !password.trim()
    ) {
      setCreateError("Please fill in all required fields.");
      return;
    }

    const employeeData: EmployeeCreateRequest = {
      firstName: firstName.trim(),
      lastName: lastName.trim(),
      email: email.trim(),
      password,
      role,
    };

    try {
      setCreating(true);
      setCreateError("");

      const newEmployee = await createEmployee(employeeData);

      setEmployees((current) => [...current, newEmployee]);

      setFirstName("");
      setLastName("");
      setEmail("");
      setPassword("");
      setRole("EMPLOYEE");

      setShowCreateForm(false);
    } catch (error) {
      if (error instanceof Error) {
        setCreateError(error.message);
      } else {
        setCreateError("Failed to create employee.");
      }
    } finally {
      setCreating(false);
    }
  };

  const handleCloseCreateForm = () => {
    if (creating) {
      return;
    }

    setShowCreateForm(false);
    setCreateError("");

    setFirstName("");
    setLastName("");
    setEmail("");
    setPassword("");
    setRole("EMPLOYEE");
  };

  if (loading) {
    return (
      <main className="flex-1 overflow-auto p-6">
        <p className="text-gray-600 dark:text-gray-400">Loading...</p>
      </main>
    );
  }

  if (error) {
    return (
      <main className="flex-1 overflow-auto p-6">
        <p className="text-red-600">{error}</p>
      </main>
    );
  }

  return (
    <main className="relative flex-1 overflow-auto p-6">
      <div className="mb-6 flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-gray-900 dark:text-white">
            Employees
          </h1>

          <p className="text-gray-500 dark:text-gray-400">Manage employees</p>
        </div>

        <button
          type="button"
          onClick={() => {
            setCreateError("");
            setShowCreateForm(true);
          }}
          className="rounded-lg bg-black px-4 py-2 text-sm font-medium text-white hover:opacity-90"
        >
          + New Employee
        </button>
      </div>

      <div className="mb-6 grid grid-cols-1 gap-4 md:grid-cols-2">
        <input
          type="text"
          placeholder="Search by first name"
          value={firstNameSearch}
          onChange={(event) => setFirstNameSearch(event.target.value)}
          className="rounded-lg border border-gray-300 bg-white px-4 py-2 outline-none focus:ring-2 dark:border-gray-700 dark:bg-gray-900 dark:text-white"
        />

        <input
          type="text"
          placeholder="Search by last name"
          value={lastNameSearch}
          onChange={(event) => setLastNameSearch(event.target.value)}
          className="rounded-lg border border-gray-300 bg-white px-4 py-2 outline-none focus:ring-2 dark:border-gray-700 dark:bg-gray-900 dark:text-white"
        />
      </div>

      <div className="overflow-hidden rounded-lg border border-gray-200 bg-white dark:border-gray-800 dark:bg-gray-900">
        <table className="w-full">
          <thead className="border-b border-gray-200 dark:border-gray-800">
            <tr>
              <th className="px-6 py-4 text-left text-sm font-semibold text-gray-900 dark:text-white">
                First name
              </th>

              <th className="px-6 py-4 text-left text-sm font-semibold text-gray-900 dark:text-white">
                Last name
              </th>

              <th className="px-6 py-4 text-left text-sm font-semibold text-gray-900 dark:text-white">
                Email
              </th>

              <th className="px-6 py-4 text-left text-sm font-semibold text-gray-900 dark:text-white">
                Role
              </th>

              <th className="px-6 py-4 text-left text-sm font-semibold text-gray-900 dark:text-white">
                Status
              </th>
            </tr>
          </thead>

          <tbody>
            {filteredEmployees.map((employee) => (
              <tr
                key={employee.employeeId}
                onClick={() => navigate(`/employees/${employee.employeeId}`)}
                className="cursor-pointer border-b border-gray-200 hover:bg-gray-50 dark:border-gray-800 dark:hover:bg-gray-800"
              >
                <td className="px-6 py-4 text-gray-900 dark:text-white">
                  {employee.firstName}
                </td>

                <td className="px-6 py-4 text-gray-900 dark:text-white">
                  {employee.lastName}
                </td>

                <td className="px-6 py-4 text-gray-700 dark:text-gray-300">
                  {employee.email}
                </td>

                <td className="px-6 py-4 text-gray-700 dark:text-gray-300">
                  {employee.role}
                </td>

                <td className="px-6 py-4 text-gray-700 dark:text-gray-300">
                  {employee.status}
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {filteredEmployees.length === 0 && (
          <div className="p-6 text-center text-gray-500 dark:text-gray-400">
            No employees found.
          </div>
        )}
      </div>

      {showCreateForm && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/50 p-4">
          <div className="w-full max-w-lg rounded-lg bg-white p-6 shadow-xl dark:bg-gray-900">
            <div className="mb-6 flex items-center justify-between">
              <h2 className="text-xl font-bold text-gray-900 dark:text-white">
                New Employee
              </h2>

              <button
                type="button"
                onClick={handleCloseCreateForm}
                disabled={creating}
                className="text-gray-500 hover:text-gray-900 disabled:opacity-50 dark:hover:text-white"
              >
                ✕
              </button>
            </div>

            {createError && (
              <div className="mb-4 rounded-lg border border-red-300 bg-red-50 p-3 text-sm text-red-700">
                {createError}
              </div>
            )}

            <div className="space-y-4">
              <div>
                <label className="mb-1 block text-sm font-medium text-gray-900 dark:text-white">
                  First name
                </label>

                <input
                  type="text"
                  value={firstName}
                  required
                  onChange={(event) => setFirstName(event.target.value)}
                  className="w-full rounded-lg border border-gray-300 px-4 py-2 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
                />
              </div>

              <div>
                <label className="mb-1 block text-sm font-medium text-gray-900 dark:text-white">
                  Last name
                </label>

                <input
                  type="text"
                  value={lastName}
                  required
                  onChange={(event) => setLastName(event.target.value)}
                  className="w-full rounded-lg border border-gray-300 px-4 py-2 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
                />
              </div>

              <div>
                <label className="mb-1 block text-sm font-medium text-gray-900 dark:text-white">
                  Email
                </label>

                <input
                  type="email"
                  value={email}
                  required
                  onChange={(event) => setEmail(event.target.value)}
                  className="w-full rounded-lg border border-gray-300 px-4 py-2 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
                />
              </div>

              <div>
                <label className="mb-1 block text-sm font-medium text-gray-900 dark:text-white">
                  Password
                </label>

                <input
                  type="password"
                  value={password}
                  onChange={(event) => setPassword(event.target.value)}
                  className="w-full rounded-lg border border-gray-300 px-4 py-2 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
                />
              </div>

              <div>
                <label className="mb-1 block text-sm font-medium text-gray-900 dark:text-white">
                  Role
                </label>

                <select
                  value={role}
                  onChange={(event) =>
                    setRole(event.target.value as EmployeeRole)
                  }
                  className="w-full rounded-lg border border-gray-300 px-4 py-2 dark:border-gray-700 dark:bg-gray-800 dark:text-white"
                >
                  <option value="EMPLOYEE">EMPLOYEE</option>

                  <option value="ADMIN">ADMIN</option>
                </select>
              </div>
            </div>

            <div className="mt-6 flex justify-end gap-3">
              <button
                type="button"
                onClick={handleCloseCreateForm}
                disabled={creating}
                className="rounded-lg border border-gray-300 px-4 py-2 text-gray-900 hover:bg-gray-100 disabled:opacity-50 dark:border-gray-700 dark:text-white dark:hover:bg-gray-800"
              >
                Cancel
              </button>

              <button
                type="button"
                onClick={handleCreateEmployee}
                disabled={creating}
                className="rounded-lg bg-black px-4 py-2 text-white hover:bg-gray-800 disabled:opacity-50 dark:bg-white dark:text-black dark:hover:bg-gray-200"
              >
                {creating ? "Creating..." : "Create Employee"}
              </button>
            </div>
          </div>
        </div>
      )}
    </main>
  );
}

export default Employees;
