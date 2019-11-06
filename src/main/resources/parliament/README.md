# Parliament TYpes, Edges

	
![parliament-datatypes](../../../../docs/graph-parliaments.png)

## Vertices

1. Parliaments
2. Electorates (by Province) - urn:aec.gov.au:division:{name}
3. Senate Seats (Numbered per state, territory e.g. AU-QLD-1)
4. Parliamentarians (People)
5. Parties

AEC JSON Parties
https://www.aec.gov.au/Parties_and_Representatives/Party_Registration/Registered_parties/files/register-2019-11-04.json

## Edges

1. Electorates to Locations.provinces
2. Countries to Electorates (External territories of Australia)
3. Parliamentarians to Electorates (by Parliament ID)
4. Parliamentarians to Parties (Effective date)
5. Parliamentarians to Senate seats
6. Cabinet to Parliamentarians (by Parliament ID, Date)
7. Parliamentarian to Parliament Role ( by Parliament ID, date, roleName) e.g. Speaker, Prime Minister, Opposition Leader,..
8. Parliamentarian to Party (Date)
