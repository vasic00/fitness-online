const express = require('express');
const axios = require('axios');
const cors = require('cors');
const xml2js = require('xml2js');
const app = express();
const PORT = process.env.PORT || 3000;

app.use(cors());

app.get('/rss-feed', async (req, res) => {
    try {
        // const feedUrl = 'https://feeds.feedburner.com/feedburner/xxu1ntbxiam';
        const feedUrl = "https://feeds.feedburner.com/AceFitFacts";
        const response = await axios.get(feedUrl);
        xml2js.parseString(response.data, (err, result) => {
            if (err) {
                console.error('Error parsing XML:', err);
                res.status(500).json({ error: 'An error occurred while parsing XML.' });
            } else {
                res.json(result);
            }
        });
    } catch (error) {
        console.error('Error fetching RSS feed:', error);
        res.status(500).json({ error: 'An error occurred while fetching the RSS feed.' });
    }
});

app.listen(PORT, () => {
    console.log(`Server is running on port ${PORT}`);
});