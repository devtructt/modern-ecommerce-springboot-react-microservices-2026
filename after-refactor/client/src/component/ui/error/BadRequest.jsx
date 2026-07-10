import React from 'react';
import badRequestImage from '../../../images/bad-request-400.png';
import { ErrorImage } from './ErrorImage';

function BadRequest() {
    return <ErrorImage image={badRequestImage} alt="bad-request-image" />;
}

export default BadRequest;