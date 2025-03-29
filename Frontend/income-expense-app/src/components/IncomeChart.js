import React from "react";
import { Bar } from "react-chartjs-2";
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from "chart.js";

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const IncomeChart = ({ data }) => {
  // Prepare data for the chart (use source as labels and amount as data)
  const chartData = {
    labels: data.map((entry) => entry.source), // Use 'source' as the label for the x-axis
    datasets: [
      {
        label: "Income", 
        data: data.map((entry) => entry.amount), // Use 'amount' as the value for the bar
        backgroundColor: "rgba(54, 162, 235, 0.6)", // Blue color for income
      },
    ],
  };

  const options = {
    responsive: true,
    plugins: {
      title: {
        display: true,
        text: "Income Source vs Amount",
      },
    },
  };

  return (
    <div style={{ width: "70%", margin: "auto", textAlign: "center" }}>
      <h1>Income Chart</h1>
      <Bar data={chartData} options={options} />
    </div>
  );
};

export default IncomeChart;
