import { useEffect, useState } from "react";
import axios from "axios";
import "./App.css";

function App() {
  const API_URL = "http://localhost:8080/api";

  const [selectedInvestorId, setSelectedInvestorId] = useState(1);
  const [portfolio, setPortfolio] = useState(null);
  const [withdrawals, setWithdrawals] = useState([]);
  const [formData, setFormData] = useState({
    productId: "",
    withdrawalAmount: "",
  });
  const [message, setMessage] = useState("");
  const [error, setError] = useState("");
  const [showSuccessModal, setShowSuccessModal] = useState(false);

  const investors = [
    { id: 1, name: "Surprise Ramatlhare" },
    { id: 2, name: "Kabelo Madisha" },
    { id: 3, name: "Thato Singo" },
  ];

  useEffect(() => {
    loadData(selectedInvestorId);
  }, [selectedInvestorId]);

  const loadData = async (investorId) => {
    try {
      const portfolioResponse = await axios.get(
        `${API_URL}/investors/${investorId}/portfolio`
      );

      const withdrawalsResponse = await axios.get(`${API_URL}/withdrawals`);

      setPortfolio(portfolioResponse.data);
      setWithdrawals(withdrawalsResponse.data);
      setFormData({ productId: "", withdrawalAmount: "" });
      setError("");
    } catch (err) {
      setError("Could not load investor information.");
    }
  };

  const totalPortfolioValue =
    portfolio?.products?.reduce(
      (total, product) => total + Number(product.currentBalance),
      0
    ) || 0;

  const getStatusClass = (status) => {
    if (status === "APPROVED") return "badge approved";
    if (status === "REJECTED") return "badge rejected";
    return "badge pending";
  };

  const handleInvestorChange = (e) => {
    setSelectedInvestorId(Number(e.target.value));
    setMessage("");
    setError("");
  };

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const submitWithdrawal = async (e) => {
    e.preventDefault();
    setMessage("");
    setError("");

    try {
      const response = await axios.post(`${API_URL}/withdrawals`, {
        investorId: selectedInvestorId,
        productId: Number(formData.productId),
        withdrawalAmount: Number(formData.withdrawalAmount),
      });

      setMessage(response.data.message);
      setShowSuccessModal(true);
      setFormData({ productId: "", withdrawalAmount: "" });
      await loadData(selectedInvestorId);
    } catch (err) {
      setError(err.response?.data?.message || "Withdrawal request failed.");
    }
  };

  const downloadCsv = () => {
    window.open(`${API_URL}/withdrawals/export`, "_blank");
  };

  if (!portfolio) {
    return <h2 className="loading">Loading dashboard...</h2>;
  }

  return (
    <div className="app">
      <aside className="sidebar">
        <h2>Enviro365</h2>
        <p>Investor Portal</p>

        <nav>
          <a href="#dashboard">Dashboard</a>
          <a href="#portfolio">Portfolio</a>
          <a href="#withdrawals">Withdrawals</a>
          <a href="#history">History</a>
        </nav>
      </aside>

      <main className="main">
        <header className="topbar">
          <div>
            <p className="eyebrow">Full-Stack Assessment Project</p>
            <h1>Investor Withdrawal Dashboard</h1>
            <p>
              Manage client portfolios, withdrawal notices, business-rule
              validation and CSV statements.
            </p>
          </div>

          <button className="export-btn" onClick={downloadCsv}>
            Download CSV
          </button>
        </header>

        <section id="dashboard" className="stats-grid">
          <div className="stat-card">
            <div className="stat-icon">👤</div>
            <span>Selected Investor</span>
            <strong>{portfolio.fullName}</strong>
          </div>

          <div className="stat-card">
            <div className="stat-icon">🎂</div>
            <span>Age</span>
            <strong>{portfolio.age}</strong>
          </div>

          <div className="stat-card">
            <div className="stat-icon">📦</div>
            <span>Products</span>
            <strong>{portfolio.products.length}</strong>
          </div>

          <div className="stat-card">
            <div className="stat-icon">💰</div>
            <span>Total Portfolio</span>
            <strong>R {totalPortfolioValue.toLocaleString()}</strong>
          </div>
        </section>

        <section id="portfolio" className="content-grid">
          <div className="panel">
            <h2>Client Portfolio</h2>

            <label className="field">
              Select Investor
              <select value={selectedInvestorId} onChange={handleInvestorChange}>
                {investors.map((investor) => (
                  <option key={investor.id} value={investor.id}>
                    {investor.name}
                  </option>
                ))}
              </select>
            </label>

            <div className="investor-box">
              <h3>{portfolio.fullName}</h3>
              <p>{portfolio.email}</p>
              <span
                className={
                  portfolio.age > 65
                    ? "eligibility eligible"
                    : "eligibility restricted"
                }
              >
                {portfolio.age > 65
                  ? "Retirement withdrawals allowed"
                  : "Retirement withdrawals restricted"}
              </span>
            </div>

            <div className="product-grid">
              {portfolio.products.map((product) => (
                <div className="product-card" key={product.id}>
                  <span>{product.productType}</span>
                  <h3>{product.productName}</h3>
                  <p>Product ID: {product.id}</p>
                  <strong>
                    R {Number(product.currentBalance).toLocaleString()}
                  </strong>
                </div>
              ))}
            </div>
          </div>

          <div id="withdrawals" className="panel">
            <h2>Create Withdrawal Notice</h2>

            {message && <div className="success">{message}</div>}
            {error && <div className="error">{error}</div>}

            <form className="withdrawal-form" onSubmit={submitWithdrawal}>
              <label className="field">
                Investment Product
                <select
                  name="productId"
                  value={formData.productId}
                  onChange={handleChange}
                  required
                >
                  <option value="">Select a product</option>
                  {portfolio.products.map((product) => (
                    <option key={product.id} value={product.id}>
                      {product.productName} - R{" "}
                      {Number(product.currentBalance).toLocaleString()}
                    </option>
                  ))}
                </select>
              </label>

              <label className="field">
                Withdrawal Amount
                <input
                  type="number"
                  name="withdrawalAmount"
                  placeholder="Enter amount"
                  value={formData.withdrawalAmount}
                  onChange={handleChange}
                  required
                />
              </label>

              <button className="primary-btn" type="submit">
                Create Withdrawal
              </button>
            </form>

            <div className="rules-box">
              <h3>Validation Rules</h3>
              <p>Retirement withdrawals require investor age greater than 65.</p>
              <p>Withdrawal cannot exceed available balance.</p>
              <p>Withdrawal cannot exceed 90% of product balance.</p>
            </div>
          </div>
        </section>

        <section id="history" className="panel history-panel">
          <div className="section-header">
            <h2>Withdrawal History</h2>
            <button className="secondary-btn" onClick={downloadCsv}>
              Export CSV Statement
            </button>
          </div>

          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Investor</th>
                <th>Product</th>
                <th>Amount</th>
                <th>Date</th>
                <th>Status</th>
              </tr>
            </thead>

            <tbody>
              {withdrawals.length === 0 ? (
                <tr>
                  <td colSpan="6" className="empty-row">
                    No withdrawals found
                  </td>
                </tr>
              ) : (
                withdrawals.map((withdrawal) => (
                  <tr key={withdrawal.id}>
                    <td>{withdrawal.id}</td>
                    <td>{withdrawal.investorName}</td>
                    <td>{withdrawal.productName}</td>
                    <td>
                      R {Number(withdrawal.withdrawalAmount).toLocaleString()}
                    </td>
                    <td>{withdrawal.withdrawalDate}</td>
                    <td>
                      <span className={getStatusClass(withdrawal.status)}>
                        {withdrawal.status}
                      </span>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        </section>

        <footer className="footer">
          Developed by <strong>Surprise Ramatlhare</strong>
        </footer>

        {showSuccessModal && (
          <div className="modal-overlay">
            <div className="modal">
              <div className="modal-icon">✓</div>
              <h2>Withdrawal Successful</h2>
              <p>Your withdrawal notice has been created successfully.</p>

              <button
                className="primary-btn"
                onClick={() => setShowSuccessModal(false)}
              >
                Close
              </button>
            </div>
          </div>
        )}
      </main>
    </div>
  );
}

export default App;