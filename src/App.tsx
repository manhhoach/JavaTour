import { Row, Col } from 'antd';
import DestinationList from './components/DestinationList';
import MapView from './components/MapView';
import { useState } from 'react';
import Coordinates from './types/tour/Coordinates';

function App() {
  const [selectedCoords, setSelectedCoords] = useState<Coordinates | null>(null);

  return (
    <Row style={{ height: '100vh' }}>
      <Col span={6}>
        <DestinationList onSelect={(coords) => setSelectedCoords(coords)} />
      </Col>
      <Col span={18}>
        <MapView selectedCoords={selectedCoords} />
      </Col>
    </Row>
  );
}

export default App;
