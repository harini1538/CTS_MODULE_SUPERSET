import React from 'react';

function App() {
  // ✅ Working office space image from Pexels
  const sr = "https://images.pexels.com/photos/380769/pexels-photo-380769.jpeg?auto=compress&cs=tinysrgb&w=800";

  const ItemName = {
    Name: "DBS",
    Rent: 50000,
    Address: "Chennai"
  };

  const colorStyle = {
    color: ItemName.Rent <= 60000 ? "red" : "green"
  };

  const element = "Office Space";

  return (
    <div style={{ textAlign: "center", marginTop: "40px" }}>
      <h1>{element}, at Affordable Range</h1>

      {/* ✅ This will now show the office space image */}
      <img src={sr} width="25%" height="25%" alt="Office Space" />

      <div style={{ marginTop: "20px" }}>
        <h2>Name: {ItemName.Name}</h2>
        <h3 style={colorStyle}>Rent: Rs. {ItemName.Rent}</h3>
        <h3>Address: {ItemName.Address}</h3>
      </div>
    </div>
  );
}

export default App;
