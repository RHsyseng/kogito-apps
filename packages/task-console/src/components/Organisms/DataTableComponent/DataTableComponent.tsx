import React, { useMemo } from 'react';
import { Bullseye } from '@patternfly/react-core';
import ReactTable from './ReactTable';
import _ from 'lodash';
import SpinnerComponent from '../../Atoms/SpinnerComponent/SpinnerComponent';
import EmptyStateComponent from '../../Atoms/EmptyStateComponent/EmptyStateComponent';
import '@patternfly/patternfly/patternfly-addons.css';
import { useGetUserTasksByStatesQuery, NetworkStatus } from '../../../graphql/types';

const DataTableComponent: React.FC = ({}) => {

  // Graphql query 
  const {
    loading,
    error,
    data,
    refetch,
    networkStatus
  } = useGetUserTasksByStatesQuery({
    variables: {
      state: ['Ready']
    },
    fetchPolicy: 'network-only',
    notifyOnNetworkStatusChange: true
  });

  // Columns
  const columns = useMemo(
    () => [
      {
        // Build our expander column
        id: 'expander', // Make sure it has an ID
        Header: ({ getToggleAllRowsExpandedProps, isAllRowsExpanded }) => (
          <span {...getToggleAllRowsExpandedProps()}>
            {isAllRowsExpanded ? '👇' : '👉'}
          </span>
        ),
        Cell: ({ row }) =>
          // Use the row.canExpand and row.getToggleRowExpandedProps prop getter
          // to build the toggle for expanding a row
          row.canExpand ? (
            <span
              {...row.getToggleRowExpandedProps({
                style: {
                  // We can even use the row.depth property
                  // and paddingLeft to indicate the depth
                  // of the row
                  paddingLeft: `${row.depth * 2}rem`
                }
              })}
            >
              {row.isExpanded ? '👇' : '👉'}
            </span>
          ) : null
      },
      {
        Header: 'ProcessId',
        accessor: 'processId'
      },
      {
        Header: 'Name',
        accessor: 'name'
      },
      {
        Header: 'Priority',
        accessor: 'priority'
      },
      {
        Header: 'ProcessInstanceId',
        accessor: 'processInstanceId'
      },
      {
        Header: 'State',
        accessor: 'state'
      }
    ],
    []
  );

  // simulate to get child row data, this is just for demo purpose
  const fakeSubRowData = {
    id: '45a73767-5da3-49bf-9c40-d533c3e77ef3',
    name: 'Apply for visa',
    priority: '1',
    processId: 'travels',
    processInstanceId: '9ae7ce3b-d49c-4f35-b843-8ac3d22fa427',
    state: 'Ready'
  };

  if (data?.UserTaskInstances?.length > 0) {
    data.UserTaskInstances[0]['subRows'] = [fakeSubRowData];
  }

  if (loading) {
    return (
      <Bullseye>
        <SpinnerComponent spinnerText="Loading user tasks..." />
      </Bullseye>
    );
  }

  if (networkStatus === NetworkStatus.refetch) {
    return (
      <Bullseye>
        <SpinnerComponent spinnerText="Loading user tasks..." />
      </Bullseye>
    );
  }

  if (error) {
    return (
      <div className=".pf-u-my-xl">
        <EmptyStateComponent
          iconType="warningTriangleIcon"
          title="Oops... error while loading"
          body="Try using the refresh action to reload user tasks"
          refetch={refetch}
        />
      </div>
    );
  }

  return (
    <>
      {!loading && (
        <ReactTable columns={columns} data={data.UserTaskInstances} />
      )}
      {data !== undefined &&
        !loading &&
        data.UserTaskInstances?.length === 0 && (
          <EmptyStateComponent
            iconType="searchIcon"
            title="No results found"
            body="Try using different filters"
          />
        )}
    </>
  );
};

export default DataTableComponent;
