import React, { useEffect, useState } from "react";
import { Bar, Pie } from "react-chartjs-2";
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, ArcElement, Title, Tooltip, Legend } from "chart.js";

ChartJS.register(CategoryScale, LinearScale, BarElement, ArcElement, Title, Tooltip, Legend);

const ExpensesChart = () => {
  const [data, setData] = useState([]);
  const [error, setError] = useState(null); // For handling errors

  useEffect(() => {
    const token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huZG9lIiwiaWF0IjoxNzQzMTc5NTU4LCJleHAiOjE3NDMxODI1NTh9.GHr10nNNQSKsVJsTZGoM9tkVhJn1BbQUC8nmuZQp2bY";
    
    if (!token) {
      setError("No authentication token found");
      return; // Exit early if no token
    }

    fetch("http://localhost:9001/api/expense/1234561", {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`, // Use the token from localStorage
      },
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error("Failed to fetch data. Please try again later.");
        }
        return response.json();
      })
      .then((result) => {
        console.log("API Response:", result);
        const formattedData = Array.isArray(result) ? result : [result]; // Ensure the data is an array
        setData(formattedData);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
        setError(error.message); // Display error message in state
        setData([]); // Ensure data is an empty array in case of error
      });
  }, []);

  const barData = {
    labels: data.map((entry) => entry.category),
    datasets: [
      {
        label: "Expense Amount (USD)",
        data: data.map((entry) => entry.amount),
        backgroundColor: "rgba(255, 99, 132, 0.6)", // Red color for bars
      },
    ],
  };

  const pieData = {
    labels: data.map((entry) => entry.expenseDate.split("T")[0]), // Extract YYYY-MM-DD from ISO date
    datasets: [
      {
        label: "Expense Distribution by Date",
        data: data.map((entry) => entry.amount),
        backgroundColor: ["#FF6384", "#36A2EB", "#FFCE56", "#4BC0C0", "#9966FF"], // Colors
      },
    ],
  };

  const options = {
    responsive: true,
    plugins: {
      title: {
        display: true,
        text: "Expenses Breakdown",
      },
    },
  };

  return (
    <div style={{ width: "80%", margin: "auto", textAlign: "center" }}>
      <h1>Expenses Chart</h1>
      {error && <p style={{ color: "red" }}>{error}</p>} {/* Show error message if there's an error */}
      <Bar data={barData} options={options} />
      <h2>Expense Distribution</h2>
      <Pie data={pieData} />
    </div>
  );
};

export default ExpensesChart;
