pragma solidity >0.6.0 <=0.9.0;


contract Vote{

    struct Voter{
        uint weight;
        bool voted;
        uint vote;
    }

    struct Proposal {
        uint count;
    }

    address chairperson;

    mapping(address=> Voter) voters;

    Proposal[] proposals;


    modifier onlyChairperson(){
        require(msg.sender==chairperson);
        _;
    }

    modifier validVoter(){
        require(voters[msg.sender].weight>0, "Not registered");
        _;
    }

    constructor(uint proposalsNum) public {
        chairperson = msg.sender;
        voters[chairperson].weight = 2;
        for (uint prop=0;prop<proposalsNum;prop++){
            proposals.push(Proposal(0));
        }
    }


    function register(address voter) public onlyChairperson {
        require (!voters[voter].voted,"Already voted");
        require (voters[voter].weight==0,"Weight not 0");

        voters[voter].weight=1;
        voters[voter].voted=false;
    }

    function vote(uint proposal) public validVoter{
        Voter storage sender = voters[msg.sender];

        require(!sender.voted,"Already voted");

        sender.voted=true;
        sender.vote = proposal;
        proposals[proposal].count +=sender.weight;
    }

    function calcWinner() public view returns(uint winProposal){
          uint winningCount = 0;

          for(uint prop =0; prop<proposals.length;prop++){
              if (proposals[prop].count > winningCount){
                  winningCount = proposals[prop].count;
                  winProposal = prop;
              }
          }


    }
}



